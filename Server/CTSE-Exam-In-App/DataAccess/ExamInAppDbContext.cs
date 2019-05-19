using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CTSE_Exam_In_App.Models;
using Microsoft.EntityFrameworkCore;

namespace CTSE_Exam_In_App.DataAccess
{
    public class ExamInAppDbContext : DbContext
    {
        #region DbSets
        
        public DbSet<User> Users { get; set; }
        public DbSet<UserPassword> UserPasswords { get; set; }
        public DbSet<Question> Questions { get; set; }
        public DbSet<Answer> Answers { get; set; }
        public DbSet<Exam> Exams { get; set; }
        public DbSet<StudentExam> StudentExams { get; set; }
        public DbSet<StudentQuestionAnswer> StudentQuestionAnswers { get; set; }

        #endregion


        public ExamInAppDbContext(DbContextOptions<ExamInAppDbContext> options) : base(options)
        { }

        /// <summary>
        /// Override OnModelCreating
        /// </summary>
        /// <param name="modelBuilder">ModelBuilder</param>
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            #region Database Table Configuration

            modelBuilder.Entity<User>()
                .HasOne(u => u.UserPassword)
                .WithOne(up => up.User)
                .HasForeignKey<UserPassword>(up => up.UserId)
                .OnDelete(DeleteBehavior.Restrict);

            modelBuilder.Entity<Exam>()
                .HasOne(e => e.LecturerUser)
                .WithMany(u => u.OwnedExams)
                .HasForeignKey(e => e.LecturerUserId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Question>()
                .HasOne(q => q.Exam)
                .WithMany(e => e.Questions)
                .HasForeignKey(q => q.ExamId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Answer>()
                .HasOne(a => a.Question)
                .WithMany(q => q.Answers)
                .HasForeignKey(a => a.QuestionId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<StudentExam>()
                .HasOne(se => se.Exam)
                .WithMany(e => e.StudentExams)
                .HasForeignKey(se => se.ExamId)
                .OnDelete(DeleteBehavior.Cascade);
            modelBuilder.Entity<StudentExam>()
                .HasOne(se => se.StudentUser)
                .WithMany(u => u.StudentExams)
                .HasForeignKey(se => se.StudentUserId)
                .OnDelete(DeleteBehavior.Restrict);

            modelBuilder.Entity<StudentQuestionAnswer>()
                .HasOne(sqa => sqa.StudentUser)
                .WithMany(u => u.StudentQuestionAnswers)
                .HasForeignKey(sqa => sqa.StudentUserId)
                .OnDelete(DeleteBehavior.Restrict);
            modelBuilder.Entity<StudentQuestionAnswer>()
                .HasOne(sqa => sqa.Question)
                .WithMany(q => q.StudentQuestionAnswers)
                .HasForeignKey(sqa => sqa.QuestionId)
                .OnDelete(DeleteBehavior.Cascade);
            modelBuilder.Entity<StudentQuestionAnswer>()
                .HasOne(sqa => sqa.Answer)
                .WithMany(a => a.StudentQuestionAnswers)
                .HasForeignKey(sqa => sqa.AnswerId)
                .OnDelete(DeleteBehavior.Cascade);

            #endregion

            base.OnModelCreating(modelBuilder);
        }
    }
}
