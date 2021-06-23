
// Book ��ü
public class Book extends Item {
	
	// ��� ����
	private String editor;

	// ������ 
	public Book(String title, String creator, String editor, String year, String image, int score, String story, String review
			) {
		super(title, creator, year, image, score, story, review);
		this.editor = editor;
	}

	// ������ ������, ������ �޼ҵ� ����
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}

}
