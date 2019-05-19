using System.Collections.Generic;

namespace CTSE_Exam_In_App.Models
{
    public class Answer
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public bool IsValid { get; set; }

        public int QuestionId { get; set; }
        public Question Question { get; set; }

        public List<StudentQuestionAnswer> StudentQuestionAnswers { get; set; }

        public Answer()
        {
            StudentQuestionAnswers = new List<StudentQuestionAnswer>();
        }
    }
}
