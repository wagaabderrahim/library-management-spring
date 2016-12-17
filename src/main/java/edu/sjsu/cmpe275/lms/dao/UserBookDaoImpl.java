package edu.sjsu.cmpe275.lms.dao;

import edu.sjsu.cmpe275.lms.entity.UserBook;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Transactional
@Repository
public class UserBookDaoImpl implements UserBookDao {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    /**
     * Returns number of books the user is holding on a particular day
     *
     * @param userId
     * @return number of books issued by user on current date
     */
    @Override
    public int getUserDayBookCount(int userId) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String query = "Select ub From UserBook ub WHERE ub.user.id = :userid AND ub.checkout_date = :checkout_date";
        Query q = entityManager.createQuery(query, UserBook.class);
        q.setParameter("userid", userId);
        q.setParameter("checkout_date", dtf.format(LocalDate.now()));
        return q.getResultList().size();
    }

    /**
     * @param bookid
     * @return
     */
    @Override
    public boolean exists(Integer bookid) {
        boolean flag = true;
        String query = "Select ub From UserBook ub WHERE ub.book.bookId = :book_id";
        Query q = entityManager.createQuery(query, UserBook.class);
        q.setParameter("book_id", bookid);
        if (q.getResultList().isEmpty()) {
            flag = false;
        }
        return flag;
    }


}
