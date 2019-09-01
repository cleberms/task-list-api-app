package br.com.challenge.tasklistapp.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "contador")
public class Counter {

    @Id
    private String uid;

    @Field("codigo")
    private String id;

    @Field("numero")
    private long seq;
}
