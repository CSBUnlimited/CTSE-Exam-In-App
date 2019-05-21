﻿// <auto-generated />
using System;
using CTSE_Exam_In_App.DataAccess;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace CTSE_Exam_In_App.Migrations
{
    [DbContext(typeof(ExamInAppDbContext))]
    [Migration("20190519174242_NameAddedToExam")]
    partial class NameAddedToExam
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062");

            modelBuilder.Entity("CTSE_Exam_In_App.Models.Answer", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Description");

                    b.Property<bool>("IsValid");

                    b.Property<int>("QuestionId");

                    b.HasKey("Id");

                    b.HasIndex("QuestionId");

                    b.ToTable("Answers");
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.Exam", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Description");

                    b.Property<DateTime>("EffectiveDateTime");

                    b.Property<DateTime>("ExpireDateTime");

                    b.Property<int>("GivenTimeSeconds");

                    b.Property<bool>("IsPublish");

                    b.Property<int>("LecturerUserId");

                    b.Property<float>("Marks");

                    b.Property<string>("Name");

                    b.HasKey("Id");

                    b.HasIndex("LecturerUserId");

                    b.ToTable("Exams");
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.Question", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Description");

                    b.Property<int>("ExamId");

                    b.Property<float>("Marks");

                    b.HasKey("Id");

                    b.HasIndex("ExamId");

                    b.ToTable("Questions");
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.StudentExam", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("EndedDateTime");

                    b.Property<int>("ExamId");

                    b.Property<float>("Marks");

                    b.Property<DateTime>("StartedDateTime");

                    b.Property<int>("StudentUserId");

                    b.HasKey("Id");

                    b.HasIndex("ExamId");

                    b.HasIndex("StudentUserId");

                    b.ToTable("StudentExams");
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.StudentQuestionAnswer", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("AnswerId");

                    b.Property<int>("QuestionId");

                    b.Property<int>("StudentUserId");

                    b.HasKey("Id");

                    b.HasIndex("AnswerId");

                    b.HasIndex("QuestionId");

                    b.HasIndex("StudentUserId");

                    b.ToTable("StudentQuestionAnswers");
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.User", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Name");

                    b.Property<Guid?>("SessionId");

                    b.Property<int>("UserType");

                    b.Property<string>("Username");

                    b.HasKey("Id");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.UserPassword", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Password");

                    b.Property<int>("UserId");

                    b.HasKey("Id");

                    b.HasIndex("UserId")
                        .IsUnique();

                    b.ToTable("UserPasswords");
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.Answer", b =>
                {
                    b.HasOne("CTSE_Exam_In_App.Models.Question", "Question")
                        .WithMany("Answers")
                        .HasForeignKey("QuestionId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.Exam", b =>
                {
                    b.HasOne("CTSE_Exam_In_App.Models.User", "LecturerUser")
                        .WithMany("OwnedExams")
                        .HasForeignKey("LecturerUserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.Question", b =>
                {
                    b.HasOne("CTSE_Exam_In_App.Models.Exam", "Exam")
                        .WithMany("Questions")
                        .HasForeignKey("ExamId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.StudentExam", b =>
                {
                    b.HasOne("CTSE_Exam_In_App.Models.Exam", "Exam")
                        .WithMany("StudentExams")
                        .HasForeignKey("ExamId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("CTSE_Exam_In_App.Models.User", "StudentUser")
                        .WithMany("StudentExams")
                        .HasForeignKey("StudentUserId")
                        .OnDelete(DeleteBehavior.Restrict);
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.StudentQuestionAnswer", b =>
                {
                    b.HasOne("CTSE_Exam_In_App.Models.Answer", "Answer")
                        .WithMany("StudentQuestionAnswers")
                        .HasForeignKey("AnswerId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("CTSE_Exam_In_App.Models.Question", "Question")
                        .WithMany("StudentQuestionAnswers")
                        .HasForeignKey("QuestionId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("CTSE_Exam_In_App.Models.User", "StudentUser")
                        .WithMany("StudentQuestionAnswers")
                        .HasForeignKey("StudentUserId")
                        .OnDelete(DeleteBehavior.Restrict);
                });

            modelBuilder.Entity("CTSE_Exam_In_App.Models.UserPassword", b =>
                {
                    b.HasOne("CTSE_Exam_In_App.Models.User", "User")
                        .WithOne("UserPassword")
                        .HasForeignKey("CTSE_Exam_In_App.Models.UserPassword", "UserId")
                        .OnDelete(DeleteBehavior.Restrict);
                });
#pragma warning restore 612, 618
        }
    }
}
