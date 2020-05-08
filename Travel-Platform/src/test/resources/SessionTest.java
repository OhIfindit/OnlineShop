package test.resources;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.jupiter.api.Test;

import cn.edu.bitzh.tp.model.Note;
import cn.edu.bitzh.tp.model.NoteDtl;
import cn.edu.bitzh.tp.util.HibernateSessionFactory;

/**
 * @author 古学懂_Victor
 * @date 2020年5月7日
 */
class SessionTest {
	Session session = null;
	Transaction transaction = null;

	@Test
	void test() {
		System.out.println("Junit Test");
		session = HibernateSessionFactory.getSession();
		transaction = session.beginTransaction();
		Note note = new Note();
		NoteDtl noteDtl=new NoteDtl();
		note.setNote_author(1000);
		note.setNote_permission(1);
		note.setNote_post_date(new Date());
		noteDtl.setNote(note);
		noteDtl.setNote_header("Test");
		note.setNoteDtl(noteDtl);
		int num = Integer.parseInt(session.save(note).toString());
		transaction.commit();
		System.out.println(num);
		fail("Not yet implemented");
	}
	@After
	void after() {
		 HibernateSessionFactory.closeSession();
	}
	
}
