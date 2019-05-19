using System;
using System.Collections.Generic;

namespace CTSE_Exam_In_App.Models
{
    public class Exam
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public int GivenTimeSeconds { get; set; }
        public bool IsPublish { get; set; }
        public float Marks { get; set; }

        public DateTime EffectiveDateTime { get; set; }
        public DateTime ExpireDateTime { get; set; }

        public int LecturerUserId { get; set; }
        public User LecturerUser { get; set; }

        public List<Question> Questions { get; set; }
        public List<StudentExam> StudentExams { get; set; }

        public Exam()
        {
            EffectiveDateTime = DateTime.Now;
            Questions = new List<Question>();
            StudentExams = new List<StudentExam>();
        }
    }
}
