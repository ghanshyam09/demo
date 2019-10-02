package com.example.demo.module;

public class Questions
{
    String question;
            String option1,option2,option3,option4,answer;


    public Questions(String question, String option1, String option2, String option3, String option4, String answer) {
        this.question = question.toString();
        this.option1 = option1.toString();
        this.option2 = option2.toString();
        this.option3 = option3.toString();
        this.option4 = option4.toString();
        this.answer = answer.toString();
    }

    public Questions()
    {}

    public String getQuestion() {
        return question.toString();
    }

    public void setQuestion(String question) {
        this.question = question.toString();
    }

    public String getOption1() {
        return option1.toString();
    }

    public void setOption1(String option1) {
        this.option1 = option1.toString();
    }

    public String getOption2() {
        return option2.toString();
    }

    public void setOption2(String option2) {
        this.option2 = option2.toString();
    }

    public String getOption3() {
        return option3.toString();
    }

    public void setOption3(String option3) {
        this.option3 = option3.toString();
    }

    public String getOption4() {
        return option4.toString();
    }

    public void setOption4(String option4) {
        this.option4 = option4.toString();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer.toString();
    }
}
