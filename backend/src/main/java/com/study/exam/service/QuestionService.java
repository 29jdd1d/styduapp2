package com.study.exam.service;

import com.study.exam.entity.Question;
import com.study.exam.entity.UserAnswer;
import com.study.exam.entity.WrongQuestion;
import com.study.exam.repository.QuestionRepository;
import com.study.exam.repository.UserRepository;
import com.study.exam.repository.WrongQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final WrongQuestionRepository wrongQuestionRepository;
    private final UserRepository userRepository;

    public Page<Question> getAllQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public List<Question> getQuestionsBySubject(String subject) {
        return questionRepository.findBySubjectAndDeletedFalse(subject);
    }

    public List<Question> getQuestionsByChapter(String chapter) {
        return questionRepository.findByChapterAndDeletedFalse(chapter);
    }

    public List<Question> getQuestionsByYear(Integer year) {
        return questionRepository.findByYearAndDeletedFalse(year);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @Transactional
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Transactional
    public Question updateQuestion(Long id, Question updatedQuestion) {
        Question question = getQuestionById(id);
        
        if (updatedQuestion.getContent() != null) {
            question.setContent(updatedQuestion.getContent());
        }
        if (updatedQuestion.getOptions() != null) {
            question.setOptions(updatedQuestion.getOptions());
        }
        if (updatedQuestion.getCorrectAnswer() != null) {
            question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
        }
        if (updatedQuestion.getExplanation() != null) {
            question.setExplanation(updatedQuestion.getExplanation());
        }

        return questionRepository.save(question);
    }

    @Transactional
    public void submitAnswer(Long userId, Long questionId, String userAnswer) {
        Question question = getQuestionById(userId);
        question.setAnswerCount(question.getAnswerCount() + 1);
        
        boolean isCorrect = question.getCorrectAnswer().equalsIgnoreCase(userAnswer.trim());
        
        if (isCorrect) {
            question.setCorrectCount(question.getCorrectCount() + 1);
        } else {
            // Add to wrong questions
            WrongQuestion wrongQuestion = wrongQuestionRepository
                    .findByUserIdAndQuestionIdAndDeletedFalse(userId, questionId)
                    .orElse(new WrongQuestion());
            
            wrongQuestion.setUserId(userId);
            wrongQuestion.setQuestionId(questionId);
            wrongQuestion.setLastWrongAnswer(userAnswer);
            wrongQuestion.setWrongCount(wrongQuestion.getWrongCount() == null ? 1 : wrongQuestion.getWrongCount() + 1);
            wrongQuestion.setIsMastered(false);
            
            wrongQuestionRepository.save(wrongQuestion);
        }
        
        questionRepository.save(question);
        
        // Update user statistics
        updateUserStatistics(userId, isCorrect);
    }

    @Transactional
    public void updateUserStatistics(Long userId, boolean isCorrect) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setTotalQuestionsAnswered(user.getTotalQuestionsAnswered() + 1);
            if (isCorrect) {
                user.setCorrectAnswers(user.getCorrectAnswers() + 1);
            }
            userRepository.save(user);
        });
    }

    public List<WrongQuestion> getUserWrongQuestions(Long userId) {
        return wrongQuestionRepository.findByUserIdAndIsMasteredFalseAndDeletedFalse(userId);
    }

    @Transactional
    public void markQuestionAsMastered(Long userId, Long questionId) {
        wrongQuestionRepository.findByUserIdAndQuestionIdAndDeletedFalse(userId, questionId)
                .ifPresent(wrongQuestion -> {
                    wrongQuestion.setIsMastered(true);
                    wrongQuestionRepository.save(wrongQuestion);
                });
    }

    @Transactional
    public void deleteQuestion(Long id) {
        Question question = getQuestionById(id);
        question.setDeleted(true);
        questionRepository.save(question);
    }
}
