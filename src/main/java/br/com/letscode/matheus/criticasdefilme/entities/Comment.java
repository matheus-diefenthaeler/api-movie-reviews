package br.com.letscode.matheus.criticasdefilme.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentReply> replies;

    private Long likes = 0L;
    private Long disLikes = 0L;
    private Boolean repeated = false;
    private Long userID;

    @Column(name = "message")
    private String message;

    private Long idCitedComment;

    @ManyToOne
    @JoinColumn(name = "rating_id")
    private Rating rating;

}
