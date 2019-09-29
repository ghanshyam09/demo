package com.example.demo.module;

public class Questions
{
    String question;
            long option1,option2,option3,option4,answer;


    public Questions(String question, long option1, long option2, long option3, long option4, long answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    public Questions()
    {}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getOption1() {
        return option1;
    }

    public void setOption1(long option1) {
        this.option1 = option1;
    }

    public long getOption2() {
        return option2;
    }

    public void setOption2(long option2) {
        this.option2 = option2;
    }

    public long getOption3() {
        return option3;
    }

    public void setOption3(long option3) {
        this.option3 = option3;
    }

    public long getOption4() {
        return option4;
    }

    public void setOption4(long option4) {
        this.option4 = option4;
    }

    public long getAnswer() {
        return answer;
    }

    public void setAnswer(long answer) {
        this.answer = answer;
    }
}
