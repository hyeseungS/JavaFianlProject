import java.io.Serializable;

// Item ��ü
public class Item implements Serializable {
	
	// �������
	private String title, creator, year, image, story, review;
	private int score;

	// ������
	public Item(String title, String creator, String year, String image, int score, String story, String review) {
		super();
		this.title = title;
		this.creator = creator;
		this.year = year;
		this.image = image;
		this.story = story;
		this.review = review;
		this.score = score;
	}

	// ������ ������, ������ �޼ҵ� ����
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
