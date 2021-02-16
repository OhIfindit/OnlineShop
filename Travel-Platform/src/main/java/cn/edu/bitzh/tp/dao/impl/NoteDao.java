package cn.edu.bitzh.tp.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bitzh.tp.dao.INoteDao;
import cn.edu.bitzh.tp.model.Note;

/**
 * @author 古学懂_Victor
 */
@Repository
public class NoteDao implements INoteDao {
	Session session = null;
	Transaction transaction = null;
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Note> listBy(int type, Object value) {
		List<Note> notes;
		Query<Note> q;
		String typeStr = "";
		try {
			switch (type) {
			case ALL:
				session = sessionFactory.openSession();
				q = session.createQuery(
						"select n from Note n where n.notePermission like 'public' order by notePostDate desc");
				notes = q.list();
				if (notes.isEmpty())
					return null;
				return notes;
			case AUTHOR:
				typeStr = "noteAuthor";
				break;
			case DATE:
				typeStr = "notePostDate";
				break;
			default:
				return null;
			}
			session = sessionFactory.openSession();
			q = session.createQuery("select n from Note n where " + typeStr + "=:value");
			q.setParameter("value", value);
			notes = q.list();
			if (notes.isEmpty())
				return null;
			return notes;
		} catch (Exception x) {
			x.printStackTrace();
			return null;
		} finally {
			sessionFactory.close();
		}
	}

	@Override
	public Note get(int id) {
		try {
			session = sessionFactory.openSession();
			Note note = session.get(Note.class, id);
			return note;
		} catch (Exception x) {
			x.printStackTrace();
			return null;
		} finally {
			sessionFactory.close();
		}
	}

	@Override
	public boolean update(Note note) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Note noteT = session.get(Note.class, note.getNoteId());
			note.getNoteDtl().setNote(note);
			session.merge(note);
			session.merge(note.getNoteDtl());
			transaction.commit();
			return true;
		} catch (Exception x) {
			x.printStackTrace();
			return false;
		} finally {
			sessionFactory.close();
		}
	}

	@Override
	public int like(int id, int type) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Note note = session.load(Note.class, id);
			if (type == INoteDao.LIKE)
				note.setLikeCount(note.getLikeCount() + 1);
			else if (type == INoteDao.UNLIKE)
				note.setLikeCount(note.getLikeCount() - 1);
			session.saveOrUpdate(note);
			transaction.commit();
			return note.getLikeCount();
		} catch (Exception x) {
			x.printStackTrace();
			return 0;
		} finally {
			sessionFactory.close();
		}
	}

	@Override
	public int insert(Note note) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			note.getNoteDtl().setNote(note);
			int num = Integer.parseInt(session.save(note).toString());
			session.save(note.getNoteDtl());
			transaction.commit();
			return num;
		} catch (Exception x) {
			x.printStackTrace();
			return 0;
		} finally {
			sessionFactory.close();
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Note note = session.load(Note.class, id);
			note.getRegions().clear();
			note.setRegions(null);
			session.clear();
			session.delete(note);
			transaction.commit();
			return true;
		} catch (Exception x) {
			x.printStackTrace();
			return false;
		} finally {
			sessionFactory.close();
		}
	}

	@Override
	public List<Note> list() {
		return null;
	}

	@Override
	public List<Note> listHotestNotes() {
		List<Note> notes;
		Query<Note> q;
		try {
			session = sessionFactory.openSession();
			q = session.createQuery(
					"select n from Note n where n.notePermission like 'public' order by (likeCount + favoriteCount + commentCount) desc");
			notes = q.list();
			if (notes.isEmpty())
				return null;
			return notes;
		} catch (Exception x) {
			x.printStackTrace();
			return null;
		} finally {
			sessionFactory.close();
		}
	}

}
