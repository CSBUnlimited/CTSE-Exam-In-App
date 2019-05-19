using System;
using System.Collections.Generic;

namespace CTSE_Exam_In_App.Models
{
    public class User
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Username { get; set; }
        public Guid? SessionId { get; set; }
        public UserType UserType { get; set; }

        public UserPassword UserPassword { get; set; }

        public List<Exam> OwnedExams { get; set; }
        public List<StudentExam> StudentExams { get; set; }
        public List<StudentQuestionAnswer> StudentQuestionAnswers { get; set; }

        public User()
        {
            OwnedExams = new List<Exam>();
            StudentExams = new List<StudentExam>();
            StudentQuestionAnswers = new List<StudentQuestionAnswer>();
        }
    }

    public enum UserType
    {
        Student = 1,
        Lecturer = 2
    }
}
