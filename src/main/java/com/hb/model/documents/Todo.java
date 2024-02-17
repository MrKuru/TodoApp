package com.hb.model.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
@Scope(value = "dev")
@Collection(value = "todo")
public class Todo {
    @Id
    private Long id;

    private String listName;
    @Field("aid")
    private Long accountId;
}
