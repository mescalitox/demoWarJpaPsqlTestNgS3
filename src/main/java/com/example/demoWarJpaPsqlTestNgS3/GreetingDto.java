/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

/**
 * @author x173117
 *
 */
public class GreetingDto {

    private long id;
    private String content;

    public GreetingDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public GreetingDto(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
