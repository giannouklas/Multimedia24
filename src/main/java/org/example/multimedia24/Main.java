package org.example.multimedia24;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.multimedia24.Models.*;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serializeAllLists();
            System.out.println("Application is shutting down. Lists have been serialized.");
        }));

        System.out.println("\nDeserialize Members... ");
        List<Member> lm = (List<Member>) Utilities.deserializeList("members.ser");
        Member.setMemberList(lm);
        int i=1;
        for (Member mem : Member.getMemberList()) {
            System.out.println("User "+i +" "+ mem.toString());
            i++;
        }

        System.out.println("\nDeserializing Admins... ");
        List<Admin> la = (List<Admin>) Utilities.deserializeList("admins.ser");
        Admin.setAdminList(la);
        i=1;
        for (Admin a : Admin.getAdminList()) {
            System.out.println("Admin "+i+" "+a.getUsername());
            i++;
        }

        System.out.println("\nDeserializing Categories... ");
        List<Category> lc = (List<Category>) Utilities.deserializeList("categories.ser");
        Category.setCategoryList(lc);
        i = 1;
        for (Category c : Category.getCategoryList()) {
            System.out.println("Category " + i + " " + c.getName());
            i++;
        }

        System.out.println("\nDeserializing Books now ");
        List<Book> lb = (List<Book>) Utilities.deserializeList("books.ser");
        Book.setBookList(lb);
        i=1;
        for (Book b : Book.getBookList()) {
            System.out.println("Book "+i+": "+b.toString());
            i++;
        }

        System.out.println("\nDeserializing Loans now ");
        List<Loan> ll = (List<Loan>) Utilities.deserializeList("loans.ser");
        Loan.setLoanList(ll);
        i=1;
        for (Loan l : Loan.getLoanList()) {
            l.checkAndUpdateLoanStatus(); //check all the loans to find if there are some that have expired
            System.out.println("Loan "+i+": "+l.toString());
            i++;
        }

        System.out.println("\nDeserializing Ratings now ");
        List<Rating> lr = (List<Rating>) Utilities.deserializeList("ratings.ser");
        Rating.setRatingList(lr);
        i=1;
        for (Rating r : Rating.getRatingList()) {
            System.out.println("Rating " + i + ": " + r.toString());
            i++;
        }

        System.out.println("\nDeserializing Comments now ");
        List<Comment> lcom = (List<Comment>) Utilities.deserializeList("comments.ser");
        Comment.setCommentList(lcom);
        i=1;
        for (Comment c : Comment.getCommentList()) {
            System.out.println("Comment " + i + ": " + c.toString()+" for "+c.getBookTitle());
            i++;
        }

        launch();
    }

    private static void serializeAllLists() {
        Utilities.serializeList(Book.getBookList(), "books.ser");
        Utilities.serializeList(Member.getMemberList(), "members.ser");
        Utilities.serializeList(Admin.getAdminList(), "admins.ser");
        Utilities.serializeList(Category.getCategoryList(), "categories.ser");
        Utilities.serializeList(Loan.getLoanList(), "loans.ser");
        Utilities.serializeList(Rating.getRatingList(), "ratings.ser");
        Utilities.serializeList(Comment.getCommentList(), "comments.ser");
    }
}