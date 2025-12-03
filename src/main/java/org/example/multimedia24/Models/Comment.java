package org.example.multimedia24.Models;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comment extends Feedback{
    @Serial
    private static final long serialVersionUID = 1L;

    private String text;
    private static List<Comment> CommentList = Collections.synchronizedList(new ArrayList<>());

    //constructor
    private Comment(Member m, Book b, String s) {
        super(m, b);
        this.text = s;
    }

    /**
     * Checks for an existing comment by a specific member for a specific book and updates it with a new text if found.
     * If no existing comment is found, a new comment is created and added to the list of comments.
     * <p>
     * The method first searches through the existing comments for a match with the given member and book. If a match is found,
     * the comment's text is updated. If no match is found, the method validates the existence of the member and the book.
     * If both are valid, a new comment is added. Otherwise, an appropriate error message is returned.
     * <p>
     * Note: The method returns {@code null} to indicate that a new comment was successfully created and added to the list.
     *
     * @param m the {@link Member} who is creating or updating the comment
     * @param b the {@link Book} the comment is about
     * @param t the text of the comment
     * @return a string indicating the outcome: "Comment updated" if an existing comment was updated, an error message if
     *         either the member or book does not exist, or {@code null} if a new comment was successfully created
     * @see Comment
     */
    public static String checkCreateAddOrUpdate(Member m, Book b, String t){
        boolean memberExists = false, bookExists=false;
        for(Comment comment : CommentList){
            if(comment.getUsername().equals(m.getUsername()) && comment.getBookISBN().equals(b.getISBN())){//if there is already a Comment by member m then update it
                comment.setText(t);
                return "Comment updated";
            }
        }//else check m and b and then create a new Comment to add to the list
        for (Member member : Member.getMemberList()){
            if (m.getUsername().equals(member.getUsername())){
                memberExists = true;
                for (Book book : Book.getBookList()){
                    if (b.getISBN().equals(book.getISBN())) {
                        bookExists = true;
                        break;
                    }
                }
            }
        }
        if(!bookExists){
            return "The book you are trying to comment on does not exist";
        }
        if(!memberExists){
            return "Error. Your Member info is invalid. Please report this to the administrator.";
        }
        Comment newComment = new Comment(m,b,t);
        CommentList.add(newComment);
        return null; //New Comment created and added to the list
    }

    /**
     * Searches for a specific comment made by a member on a specific book and returns it(if it exists).
     * <p>
     * This method iterates over all comments related to the book with the specified ISBN. If a comment made
     * by the given member is found, it is returned immediately. Otherwise, if no such comment exists, the method
     * returns {@code null}.
     *
     * @param m the {@link Member} who made the comment
     * @param isbn the ISBN of the book for which the comment was made
     * @return the {@link Comment} made by the specified member on the book with the given ISBN, or {@code null} if not found
     */
    public static Comment findSpecificComment (Member m, String isbn){
        List<Comment> lc = getCommentsForSpecificBook(isbn);
        for (Comment c: lc){
            if(c.getUsername().equals(m.getUsername()))
                return c; //found comment c made by member m
        }
        return null; //comment not found
    }

    /**
     * Retrieves the text of this comment.
     *
     * @return the text of the comment as a {@link String}
     */
    public String getText(){return text;}

    /**
     * Returns the list of all comments.
     *
     * @return a {@link List} of {@link Comment} objects representing all comments
     */
    public static List<Comment> getCommentList() {return CommentList;}

    /**
     * Finds all comments (if there are any) associated with a specific book identified by its ISBN.
     * <p>
     * This method searches through all comments to find those that are related to the book with the given ISBN.
     * It returns a list including these comments.
     *
     * @param isbn the ISBN of the book for which comments are being retrieved
     * @return a {@link List} of {@link Comment} objects for the specified book
     */
    public static List<Comment> getCommentsForSpecificBook(String isbn){
        List<Comment> commentsForBook = new ArrayList<>();
        for(Comment c: CommentList){
            if(c.getBookISBN().equals(isbn)){
                commentsForBook.add(c);
            }
        }
        return commentsForBook;
    }

    /**
     * Sets the text of this comment.
     *
     * @param text the new text for this comment
     */
    public void setText(String text){this.text = text;}

    /**
     * Updates the list of all comments.
     *
     * @param l the new list of {@link Comment} objects to replace the current list
     */
    public static void setCommentList(List<Comment> l){CommentList = l;}

    /**
     * Provides a string representation of this comment, including the username of the member who made the comment
     * and the comment text.
     *
     * @return a string in the representing the comment
     */
    public String toString(){return this.getUsername()+" says: '"+text+"'";}

    /**
     * Attempts to remove a specific comment made by a member on a book.
     * <p>
     * This method searches for a comment matching both the specified member and book. If such a comment is found,
     * it is removed from the list of comments, and a confirmation message is returned. If no matching comment is found,
     * a "Comment not Found" message is returned.
     *
     * @param m the {@link Member} who made the comment
     * @param b the {@link Book} the comment is associated with
     * @return a message indicating whether the comment was successfully deleted or not found
     */
    public static String removeComment(Member m, Book b) {
        for (Comment c : CommentList) {
            if (c.getBookISBN().equals(b.getISBN()) && c.getUsername().equals(m.getUsername())) {
                CommentList.remove(c);
                return "The comment was deleted.";
            }
        }
        return "Comment not Found";
    }

    /**
     * Removes all comments associated with a specific book.
     * <p>
     * This method searches for and removes any comments made by for the specified book. It returns a message indicating
     * whether any comments were found and removed, or if no comments for the book were found.
     *
     * @param b the {@link Book} for which all comments are to be removed
     * @return a message detailing the result of the operation, including whether comments were removed or none were found
     */
    public static String removeAllCommentsForSpecificBook(Book b) {
        List<Comment> commentsToRemove = new ArrayList<>();
        for (Comment c : CommentList) {
            if (c.getBookISBN().equals(b.getISBN())) {
                commentsToRemove.add(c);
            }
        }
        if (commentsToRemove.isEmpty())
            return "No comments found for'" + b.getTitle() + "'.";

        for (Comment c : commentsToRemove) {
            CommentList.remove(c);
        }
        return "All comments for '" + b.getTitle() + "' were successfully removed.";
    }

    /**
     * Removes all comments made by a specific member about any one of the books.
     * <p>
     * This method searches for and removes any comments made by the specified member. It returns a message indicating
     * whether any comments were found and removed, or if no comments by the member were found.
     *
     * @param m the {@link Member} whose comments are to be removed
     * @return a message detailing the result of the operation, including whether comments were removed or none were found
     */
    public static String removeAllCommentsForSpecificMember(Member m) {
        List<Comment> commentsToRemove = new ArrayList<>();
        for (Comment c : CommentList) {
            if (c.getUsername().equals(m.getUsername())) {
                commentsToRemove.add(c);
            }
        }
        if (commentsToRemove.isEmpty()) {
            return "No comments found from " + m.getUsername() + ".";
        }
        for (Comment c : commentsToRemove) {
            CommentList.remove(c);
        }
        return "All comments from " + m.getUsername() + " were successfully removed.";
    }
}
