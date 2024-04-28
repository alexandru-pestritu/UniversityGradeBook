# UniversityGradeBook
Welcome to UniversityGradeBook - a Java console application designed for university grade management. The app provides a user-friendly interface for both teachers and students to manage courses, grades, and assignments efficiently.

## Features
- **Role-based Access Control**: Supports two user types - teacher and student, each with their respective functionalities.
- **CRUD Operations**: Teachers can perform CRUD operations on the courses assigned to them.
- **Grade Viewing**: Students can view their grades and courses.
- **CSV Data Storage**: Utilizes CSV files for storing and retrieving information, ensuring data persistence and portability.
- **Modular Design**: Organized into models, services, and a data repository singleton for efficient data handling.

## Getting Started
1. **Clone the Repository**: Clone this repository to your local machine.
   ```bash
   git clone https://github.com/alexandru-pestritu/UniversityGradeBook
   ```
2. **Compile the Application**: Navigate to the project directory and compile the application using ```javac Main.java```.
3. **Run the Application**: Run the compiled application, and follow the prompts to log in and navigate through the menus.

## Usage
- **Login**: Start by logging in as either a teacher or a student to access the corresponding functionalities.
- **Teacher Menu**: Perform CRUD operations on courses assigned to you, manage grades, and navigate through submenus for detailed actions.
- **Student Menu**: View grades, courses, and other relevant information specific to your profile.
- **Infinite Menu System**: Enjoy a seamless navigation experience with an infinite menu system that adapts to your user role and actions.
