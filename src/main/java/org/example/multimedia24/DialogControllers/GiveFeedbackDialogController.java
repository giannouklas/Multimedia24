package org.example.multimedia24.DialogControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.multimedia24.CurrentUserInfo;
import org.example.multimedia24.Models.*;
import org.example.multimedia24.Utilities;

public class GiveFeedbackDialogController {
    private final Member member = CurrentUserInfo.getCurrentMember();
    private Book book;
    Rating r;
    Comment c;

    @FXML private Label RatingErrorText;
    @FXML private Label CommentErrorText;
    @FXML private Label bookTitleLabel;
    @FXML private TextField userRating;
    @FXML private TextArea userComment;

    public void setData(Book b) {
        this.book = b;
        r = Rating.findSpecificRating(member, b.getISBN());
        c = Comment.findSpecificComment(member, b.getISBN());
        bookTitleLabel.setText(b.getTitle());

        if(r==null){
            userRating.clear();
        }
        else{
            userRating.setText(String.valueOf(r.getRate()));
        }

        if(c==null){
            userComment.clear();
        }
        else{userComment.setText(c.getText());}

        RatingErrorText.setText("");
        CommentErrorText.setText("");
    }

    @FXML void onPostRatingClick(){
        int number;
        try {
            number = Integer.valueOf(userRating.getText());
            if(number<1 ||number>5){
                Utilities.showErrorAlert("","Invalid Rating", "Your rating has to be an integer from 1 to 5 ");
            }
            else{
                String m = Rating.checkCreateAddOrUpdate(member, book, number);
                if(m == null){
                    Utilities.showInfoAlert("","Your rating has been posted", "Thank you for leaving feedback");
                }
                else{
                    Utilities.showInfoAlert("",m,"");
                }
            }
        } catch (NumberFormatException e) {
            Utilities.showErrorAlert("","Invalid Rating", "Your rating has to be an integer from 1 to 5 ");
        }
    }

    @FXML void onPostCommentClick(){
        if(userComment.getText()==null || userComment.getText().length()<2){
            Utilities.showErrorAlert("","Invalid Comment", "Your comment is too short");
        }
        else{
            String m = Comment.checkCreateAddOrUpdate(member, book, userComment.getText());
            if(m == null){
                Utilities.showInfoAlert("","Your comment has been posted", "Thank you for leaving feedback");
            }
            else{
                Utilities.showInfoAlert("",m,"");
            }
        }
    }

    @FXML void onDeleteCommentClick(){
        String m = Comment.removeComment(member, book);
        userComment.clear();
        Utilities.showInfoAlert("Comment Removal","",m);
        setData(book);
    }

    @FXML void onDeleteRatingClick(){
        String m = Rating.removeRating(member, book);
        userRating.clear();
        Utilities.showInfoAlert("Rating Removal","",m);
        setData(book);
    }

}
