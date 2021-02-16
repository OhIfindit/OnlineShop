package cn.edu.bitzh.tp.dao;

import java.util.List;

import cn.edu.bitzh.tp.model.Note;

/**
 * @author 古学懂_Victor
 */
public interface INoteDao extends IBaseDao<Note> {
	/** Used for list all notes */
	public static final int ALL = 0;
	/** Used for query by author */
	public static final int AUTHOR = 1;
	public static final int DATE = 2;
	public static final int LIKE = 3;
	public static final int UNLIKE = 4;

	/**
	 * 插入游记
	 * 
	 * @author 古学懂_Victor
	 * @return 游记主键
	 */
	@Override
	int insert(Note note);

	@Override
	boolean delete(int id);

	/**
	 * 更新游记
	 * 
	 * @author 古学懂_Victor
	 * @param note
	 * @return boolean 成功与否
	 */
	@Override
	boolean update(Note note);

	/**
	 * 根据类型获取游记列表
	 * 
	 * @author 古学懂_Victor
	 * @param type
	 * @param value
	 * @return
	 */
	List<Note> listBy(int type, Object value);

	/**
	 * 根据ID获取游记
	 * 
	 * @author 古学懂_Victor
	 * @param id
	 * @return
	 */
	@Override
	Note get(int id);

	@Override
	default List<Note> list() {
		return listBy(INoteDao.ALL, null);
	}

	List<Note> listHotestNotes();

	/**
	 * Type=3为未点赞者点赞，Type=4为已点赞者取消赞
	 * @author 古学懂_Victor
	 * @date 2020年6月20日
	 * @param id
	 * @param type 
	 * @return
	 */
	int like(int id, int type);

}
