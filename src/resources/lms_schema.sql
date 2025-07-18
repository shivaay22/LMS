-- Create the LMS database
CREATE DATABASE IF NOT EXISTS lms_db;
USE lms_db;

-- Users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('STUDENT', 'TEACHER', 'ADMIN') NOT NULL
);

-- Courses table
CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    instructor VARCHAR(100)
);

-- Enrollments table
CREATE TABLE enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id),
    UNIQUE (username, course_id)
);

-- Books table
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 1
);

-- Issued Books table
CREATE TABLE issued_books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    issue_date DATE DEFAULT CURRENT_DATE,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

-- Sample users
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'ADMIN'),
('teacher1', 'teacher123', 'TEACHER'),
('student1', 'student123', 'STUDENT');

-- Sample courses
INSERT INTO courses (title, description, instructor) VALUES
('Java Fundamentals', 'Introduction to Java', 'teacher1'),
('Data Structures', 'Understanding core DSA concepts', 'teacher1'),
('Web Development', 'HTML, CSS, JavaScript, and backend', 'teacher1'),
('Database Systems', 'Relational databases and SQL', 'teacher1');

-- Sample books
INSERT INTO books (title, author, quantity) VALUES
('Head First Java', 'Kathy Sierra', 5),
('Data Structures in Java', 'Narasimha Karumanchi', 4),
('JavaScript: The Good Parts', 'Douglas Crockford', 3),
('Clean Code', 'Robert C. Martin', 2),
('Database System Concepts', 'Silberschatz', 6),
('You Don’t Know JS', 'Kyle Simpson', 4),
('The Pragmatic Programmer', 'Andrew Hunt', 3),
('Effective Java', 'Joshua Bloch', 4),
('HTML & CSS', 'Jon Duckett', 3),
('Design Patterns', 'Erich Gamma', 2);
