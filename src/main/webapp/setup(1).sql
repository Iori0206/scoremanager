
INSERT INTO SCHOOL(CD, NAME) VALUES
('tes', 'テスト校');  

INSERT INTO CLASS_NUM(SCHOOL_CD, CLASS_NUM) VALUES
('tes', '101'),
('tes', '102'),
('tes', '201'),
('tes', '202');  


INSERT INTO TEACHER(ID, PASSWORD, NAME, SCHOOL_CD) VALUES
('admin1', 'password', '管理者1', 'tes');  


INSERT INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD)
VALUES 
    (124, 'bbb', 2024, 102, FALSE, 'tes'),
    (125, 'ccc', 2025, 201, TRUE, 'tes'),
    (126, 'ddd', 2025, 202, FALSE, 'tes'),
    (127, '大原太郎', 2025, 202, FALSE, 'tes'),
    (128, 'eeee', 2023, 101, TRUE, 'tes'),
    (129, 'ffff', 2023, 102, TRUE, 'tes'),
    (130, 'gggg', 2024, 201, FALSE, 'tes'),
    (131, 'hhhh', 2024, 202, TRUE, 'tes'),
    (132, 'iiii', 2025, 101, FALSE, 'tes'),
    (133, 'jjjj', 2025, 102, TRUE, 'tes'),
    (134, 'kkkk', 2023, 201, FALSE, 'tes'),
    (135, 'llll', 2023, 202, TRUE, 'tes'),
    (136, 'mmmm', 2024, 101, FALSE, 'tes'),
    (137, 'nnnn', 2024, 102, TRUE, 'tes'),
    (138, 'oooo', 2025, 201, FALSE, 'tes'),
    (139, 'pppp', 2025, 202, TRUE, 'tes'),
    (140, 'qqqq', 2023, 101, FALSE, 'tes'),
    (141, 'rrrr', 2023, 102, TRUE, 'tes'),
    (142, 'ssss', 2024, 201, TRUE, 'tes'),
    (143, 'tttt', 2024, 202, FALSE, 'tes');
   