import java.util.*;

// ItemCollections Ŭ����
public class ItemCollections {
	
	// ��� ����
	private Vector<Item> ic = new Vector<Item>();

	// ������
	public ItemCollections(Vector<Item> ic) {
		super();
		this.ic = ic;
	}

	// ������ ������, ������ �޼ҵ� ����
	public Vector<Item> getIc() {
		return ic;
	}
	public void setIc(Vector<Item> ic) {
		this.ic = ic;
	}
	
	// Item�� ���
	public void add(Item item) {
		ic.add(item);
	}

	// Item�� ����
	public void edit(int index, Item item) {
		if(index != -1)
			ic.set(index, item);
	}
	
	// Item�� ����
	public void remove(int index) {
		if(index != -1)
			ic.remove(index);
	}
	
	// Item "����" �˻�
	public Vector<String> searchTitle(String search) {
		
		Vector<String> sv = new Vector<String>();
        Object []arrItem  = ic.toArray();
		for(int i=0; i<ic.size(); i++) {
			Item item = (Item)arrItem[i];
			String title = item.getTitle();
			if(title.contains(search)) {
				sv.add(title);
			}
		}
		return sv;
	}
		
	// Item "����" �˻�
	public Vector<String> searchScore(String search) {
		
		Vector<String> sv = new Vector<String>();
        Object []arrItem = ic.toArray();
		for(int i=0; i<ic.size(); i++) {
			Item item = (Item)arrItem[i];
			int score = item.getScore();
			if(score >= Integer.parseInt(search)) {
				sv.add(item.getTitle());
				}
		}
		return sv;	
	}
}
