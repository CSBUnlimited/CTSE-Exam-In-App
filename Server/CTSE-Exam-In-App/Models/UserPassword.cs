namespace CTSE_Exam_In_App.Models
{
    public class UserPassword
    {
        public int Id { get; set; }
        public string Password { get; set; }
        public int UserId { get; set; }
        public User User { get; set; }
    }
}
