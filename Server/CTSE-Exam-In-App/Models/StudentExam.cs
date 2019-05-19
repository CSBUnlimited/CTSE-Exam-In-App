using System;

namespace CTSE_Exam_In_App.Models
{
    public class StudentExam
    {
        public int Id { get; set; }

        public int ExamId { get; set; }
        public Exam Exam { get; set; }

        public int StudentUserId { get; set; }
        public User StudentUser { get; set; }

        public DateTime StartedDateTime { get; set; }
        public DateTime EndedDateTime { get; set; }
        public float Marks { get; set; }
    }
}
