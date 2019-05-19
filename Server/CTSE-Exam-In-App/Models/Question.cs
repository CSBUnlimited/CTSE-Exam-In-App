using System.Collections.Generic;

namespace CTSE_Exam_In_App.Models
{
    public class Question
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public float Marks { get; set; }

        public int ExamId { get; set; }
        public Exam Exam { get; set; }

        public List<Answer> Answers { get; set; }
        public List<StudentQuestionAnswer> StudentQuestionAnswers { get; set; }

        public Question()
        {
            Answers = new List<Answer>();
            StudentQuestionAnswers = new List<StudentQuestionAnswer>();
        }
    }
}
