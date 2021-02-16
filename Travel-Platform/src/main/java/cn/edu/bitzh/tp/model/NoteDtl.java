package cn.edu.bitzh.tp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 游记内容
 * 
 * @author 古学懂_Victor
 * @date 2020年5月5日
 */
@Table
@Entity(name = "t_note_dtl")
public class NoteDtl {
	@Id
	@GenericGenerator(name = "fk", strategy = "foreign", parameters = @Parameter(name = "property", value = "note"))
	@GeneratedValue(generator = "fk")
	@Column(name = "note_id")
	private int noteId;
	@Column(name = "note_header")
	private String noteHeader;
	@Lob
	@Column(name = "note_content")
	private String noteContent;
	@Lob
	@Column(name = "note_toppic")
	private String noteToppic;
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Note note;

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getNoteHeader() {
		return noteHeader;
	}

	public void setNoteHeader(String noteHeader) {
		this.noteHeader = noteHeader;
	}

	@JSON(serialize = false)
	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	
	/**
	 * @return the noteToppic
	 */
	public String getNoteToppic() {
		return noteToppic;
	}

	/**
	 * @param noteToppic the noteToppic to set
	 */
	public void setNoteToppic(String noteToppic) {
		this.noteToppic = noteToppic;
	}
	
}
