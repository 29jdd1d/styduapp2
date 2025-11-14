-- Create default admin user
-- Username: admin
-- Password: admin123 (MD5: 0192023a7bbd73250516f069df18b500)

INSERT INTO user (id, username, password, nickname, role, openid, gender, study_days, total_study_time, total_questions_answered, correct_answers, deleted, created_time, updated_time)
SELECT * FROM (SELECT 1 as id, 'admin' as username, '0192023a7bbd73250516f069df18b500' as password, '管理员' as nickname, 'ADMIN' as role, null as openid, 0 as gender, 0 as study_days, 0 as total_study_time, 0 as total_questions_answered, 0 as correct_answers, 0 as deleted, NOW() as created_time, NOW() as updated_time) AS tmp
WHERE NOT EXISTS (
    SELECT username FROM user WHERE username = 'admin'
) LIMIT 1;
