/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author x173117
 * 
 *         CREATE TABLE public.greeting ( id SERIAL NOT NULL DEFAULT, content
 *         character varying(200) NOT NULL ), CONSTRAINT id PRIMARY KEY (id)
 * 
 *         INSERT INTO public.greeting(content) VALUES ('robert'); INSERT INTO
 *         public.greeting(content) VALUES ('gini'); INSERT INTO
 *         public.greeting(content) VALUES ('lola'); INSERT INTO
 *         public.greeting(content) VALUES ('yoda'); select * from greeting
 * 
 *
 * 
 */
@Entity
@Table(name = "greeting")
public class GreetingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    public GreetingEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

    public GreetingEntity(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EntityGreeting [id=" + id + ", content=" + content + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
