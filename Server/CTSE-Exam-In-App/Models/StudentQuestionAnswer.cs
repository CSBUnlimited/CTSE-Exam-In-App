using System;

namespace CTSE_Exam_In_App.Models
{
    public class StudentQuestionAnswer
    {
        public int Id { get; set; }

        public int StudentUserId { get; set; }
        public User StudentUser { get; set; }

        public int QuestionId { get; set; }
        public Question Question { get; set; }

        public int AnswerId { get; set; }
        public Answer Answer { get; set; }
    }
}
