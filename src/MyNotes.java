import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

public class MyNotes extends JFrame {

	// ��� ����, ������Ʈ ����
	private int c=3;
	private int choice = 1; 
	private int index, index2, index3, index4;
	private int bt = 1;
	private Vector<Item> v = new Vector<Item>();
	private Vector<String> t1 = new Vector<String>();
	private JList<String> l1 = new JList<String>(t1);
	private Vector<String> t2 = new Vector<String>();
	private JList<String> l2 = new JList<String>(t2);
	private Vector<String> t3 = new Vector<String>();
	private JList<String> l3 = new JList<String>(t3);
	Vector<String> searchv = new Vector<String>(); 
	JList<String> ls = new JList<String>(searchv);
	private ItemCollections ic = new ItemCollections(v);
	private String image;
	private JSlider slider;
	private MyModalDialog dialog = new MyModalDialog(this, "�Է�");
	private JLabel titlelb, creatorlb, actorlb, genrelb, gradelb, yearlb, imagelb, scorelb, storylb, reviewlb;
	private JTextField titletf, creatortf, actortf, imagetf;
	private JComboBox<String> genrecb;
	private JComboBox<String> gradecb;
	private JComboBox<String> yearcb;
	private JButton filebt, okbt;
	private JFileChooser chooser;
	private JTextArea storyta, reviewta;
	private JRadioButton movierb, bookrb;
	private DialogPanel dp;
	private Container cp = getContentPane();
	private MyPanel3 p3 = new MyPanel3();
	private JTextArea story;
	private JTextArea review;
	private TabPanel4 tp;
	
	public MyNotes() {
		setTitle("JAVA 004 1910646 ������"); // Ÿ��Ʋ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �������α׷� ����
		createMenu();
		cp.setLayout(new BorderLayout());
		cp.add(new MyPanel1(), BorderLayout.NORTH);
		cp.add(new MyPanel2(), BorderLayout.WEST);
		cp.add(p3, BorderLayout.CENTER);
		setSize(800,600); // ������ ũ�� ����
		setVisible(true); // ������ ȭ�鿡 ���
	}
	
	// Menu ����� �޼ҵ�
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		MenuActionListener mal = new MenuActionListener();
		// ���� �޴� (�ҷ�����, �����ϱ�, ����)
		JMenu fileMenu = new JMenu("����");
		JMenuItem openItem = new JMenuItem("�ҷ�����");
		openItem.addActionListener(mal);
		JMenuItem saveItem = new JMenuItem("�����ϱ�");
		saveItem.addActionListener(mal);
		JMenuItem exitItem = new JMenuItem("����");
		exitItem.addActionListener(mal);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		// ���� �޴�
		JMenu helpMenu = new JMenu("����");
		// ���� ���� �޼ҵ�
		JMenuItem infoItem = new JMenuItem("���� ����");
		infoItem.addActionListener(mal);
		helpMenu.add(infoItem);
		
		mb.add(fileMenu);
		mb.add(helpMenu);		
		setJMenuBar(mb);
	}
	
	// Menu Action ������ ����
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			File selectfile = null;
			File savefile = null;
			if(cmd.equals("�ҷ�����")) { // "�ҷ�����" �޴� �޴� ���� ���̾�α�
				JFileChooser chooser1 = new JFileChooser();
				v.clear();
				int ret1 = chooser1.showOpenDialog(null);
				   if(ret1 == JFileChooser.APPROVE_OPTION) {
					   selectfile = chooser1.getSelectedFile();
				   }
				   if(selectfile!=null) {
					   try {
						   ObjectInputStream ois=new ObjectInputStream(new FileInputStream(selectfile));
							v = (Vector<Item>)ois.readObject();
							Iterator<Item> it = v.iterator();
							while(it.hasNext()) {
								Item i = it.next();
								t1.add(i.getTitle());
								if(i.getClass().getName().equals("Movie")) {
									t2.add(i.getTitle());									
								}
								else
									t3.add(i.getTitle());
							}
							l1.setListData(t1); l2.setListData(t2); l3.setListData(t3);
							ois.close();
					   }catch(IOException | ClassNotFoundException i1) { i1.printStackTrace(); }
				   }
			}
			else if(cmd.equals("�����ϱ�")) { // "����" �޴� �޴� ���� ���̾�α�
				JFileChooser chooser2 = new JFileChooser();
				int ret2 = chooser2.showSaveDialog(null);
					if(ret2 == JFileChooser.APPROVE_OPTION) {
						   savefile = chooser2.getSelectedFile();
						   try {
							   ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(savefile));
							   oos.writeObject(v);
							   oos.close();
						   }catch(IOException i2) {
							   i2.printStackTrace();
							   }
					}
			}
			else if(cmd.equals("����")) { // "����" �޴�
				System.exit(0);
			}
			else if(cmd.equals("���� ����")) { // "���� ����" �޴� �˾� ���̾� �α�
				String version = "MyNotes �ý��� v 1.0 �Դϴ�.";
				   JOptionPane.showMessageDialog(null, version, "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	// �� ���κ� ("My Notes"��, ���� �ð�)
	class MyPanel1 extends JPanel {
		public MyPanel1() {
			setLayout(new BorderLayout());
			JLabel mynotes = new JLabel("My Notes", SwingConstants.CENTER);
			mynotes.setForeground(Color.BLUE);
			mynotes.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 30));
			add(mynotes, BorderLayout.CENTER);
			add(new TimeLabel(), BorderLayout.EAST);
		}
		
	}
	// ���� �κ� (����, "�߰�"��ư)
	class MyPanel2 extends JPanel {
		public MyPanel2() {
			setLayout(new BorderLayout());
			
			JTabbedPane pane = createTabbedPane(); // ����
			JButton addbt = new JButton("�߰�"); // �߰���ư
			addbt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					choice = 1;
					bt = 1;
					movierb.setSelected(true);
					dialog.remove(dp);
					DialogPanel movieDialog = new DialogPanel();
					dialog.add(movieDialog, BorderLayout.CENTER);
					dialog.revalidate();
					dp=movieDialog;
					dialog.setVisible(true);
				}
			});
			add(pane, BorderLayout.CENTER);
			JPanel panel = new JPanel();
			panel.add(addbt);
			add(panel, BorderLayout.SOUTH);
			pane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JTabbedPane t = (JTabbedPane)e.getSource();
					if(t.getSelectedIndex()==3) {
						remove(pane);
						JTabbedPane jp = createTabbedPane();
						add(jp, BorderLayout.CENTER);
						revalidate();
					}
				}
			});
		}
	}
	// ���� ����� �޼ҵ�
	private JTabbedPane createTabbedPane() {
		JTabbedPane tpane = new JTabbedPane(JTabbedPane.TOP);
		tp = new TabPanel4();
		l1.addListSelectionListener(new ListListener());
		l2.addListSelectionListener(new ListListener());
		l3.addListSelectionListener(new ListListener());
		tpane.addTab("��ü", l1);
		tpane.addTab("��ȭ", l2);
		tpane.addTab("����", l3);
		tpane.addTab("�˻�", tp);
		return tpane;
	}	
	
	// "�˻�" ��
	class TabPanel4 extends JPanel {
		
		String category = "����";
		public TabPanel4() {
			String[] sa = {"����", "����"};
			setLayout(new BorderLayout());
			JPanel panel = new JPanel();
			JComboBox<String> searchcb = new JComboBox<String>(sa);
			JTextField search = new JTextField(10);
			JButton searchbt = new JButton("�˻�");
			if(v.size() != 0) {
				searchcb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JComboBox<String> cb = (JComboBox<String>)e.getSource();
						int s = cb.getSelectedIndex();
						category = sa[s];
					}
				});
				searchbt.addActionListener(new ActionListener() { // �� ���� ��ư�� Action �̺�Ʈ ������ ���
					public void actionPerformed(ActionEvent e) {
						ls.removeAll();
						searchv.clear();
						if(category.equals("����")) {
							Iterator<String> it = (ic.searchTitle(search.getText())).iterator();
							while(it.hasNext()) {
								searchv.add(it.next());
							}
							if(searchv.size() == 0) {
								String errorMessage = "["+search.getText()+"] �˻� ����� �����ϴ�.";
								JOptionPane.showMessageDialog(null, errorMessage, "Message", JOptionPane.INFORMATION_MESSAGE);
							}
						}
						else if(category.equals("����")){
							Iterator<String> it = (ic.searchScore(search.getText())).iterator();
							while(it.hasNext()) {
								searchv.add(it.next());
							}
						}
						ls.setListData(searchv);
					}
				});
			}
			ls.addListSelectionListener(new ListListener());
			panel.add(searchcb);
			panel.add(search);
			panel.add(searchbt);
			add(panel, BorderLayout.NORTH);
			add(ls, BorderLayout.CENTER);
		}
	}
	
	class MyModalDialog extends JDialog { // ��� ���̾�α� ����

		public MyModalDialog(JFrame frame, String title) {
			super(frame, title, true); 
			setLayout(new BorderLayout());
			JPanel choose = new JPanel();
			dp = new DialogPanel();
			// ���� ��ư ����
			ButtonGroup group = new ButtonGroup();
			movierb = new JRadioButton("Movie");
			bookrb = new JRadioButton("Book");
			if(bt == 1)
				movierb.setSelected(true);
			else if(bt == 2){
				if(choice == 1)
					movierb.setSelected(true);
				else
					bookrb.setSelected(true);
			}
			
			group.add(movierb); group.add(bookrb); choose.add(movierb); choose.add(bookrb); 
			add(choose, BorderLayout.NORTH); add(dp, BorderLayout.CENTER);
			movierb.addItemListener(new MyItemListener());
			bookrb.addItemListener(new MyItemListener());
			setSize(430, 680); 
		}			
	}
	// ������ ���� ��ư ���� �Է� ��� ����
	class MyItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			dialog.remove(dp);
			if (movierb.isSelected()) { // "Movie" ������ ���
				choice = 1;
				DialogPanel movieDialog = new DialogPanel();
				dialog.add(movieDialog, BorderLayout.CENTER);
				dialog.revalidate();
				dp=movieDialog;
			}
			else if(bookrb.isSelected()) { // "Book" ������ ���
				choice = 2;
				DialogPanel bookDialog = new DialogPanel();
				dialog.add(bookDialog, BorderLayout.CENTER);
				dialog.revalidate();
				dp=bookDialog;
				
			}
		}
	}
	// ���̾�α�
	public class DialogPanel extends JPanel {
		public DialogPanel() {
			
			String []genre = {"���", "�̽��͸�", "������, ����", "�ִϸ��̼�", "�׼�"};
			String []grade = {"��ü", "12�� �̻�", "15�� �̻�", "û�ҳ� ���� �Ұ�"};
			String[] year = {"2020", "2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009",
					"2008","2007","2006","2005","2004","2003","2002","2001","2000"};
			// ������Ʈ ����
			titlelb = new JLabel("����", SwingConstants.CENTER); creatorlb = new JLabel("����", SwingConstants.CENTER); 
			actorlb = new JLabel("���", SwingConstants.CENTER); genrelb = new JLabel("�帣", SwingConstants.CENTER); 
			gradelb = new JLabel("���", SwingConstants.CENTER); yearlb = new JLabel("�����⵵", SwingConstants.CENTER); 
			imagelb = new JLabel("������", SwingConstants.CENTER); scorelb = new JLabel("����", SwingConstants.CENTER); 
			storylb = new JLabel("�ٰŸ�", SwingConstants.CENTER); reviewlb = new JLabel("������", SwingConstants.CENTER); 
			titletf = new JTextField(10); creatortf = new JTextField(10);
			actortf = new JTextField(10); imagetf = new JTextField(10); chooser = new JFileChooser(); 
			storyta = new JTextArea(4,30); reviewta = new JTextArea(4,30); 
		    genrecb = new JComboBox<String>(genre); gradecb = new JComboBox<String>(grade);
			yearcb = new JComboBox<String>(year); filebt = new JButton("�ҷ�����"); okbt = new JButton("OK");
			slider = new JSlider(JSlider.HORIZONTAL,1,10,5);
			slider.setPaintLabels(true); slider.setPaintTicks(true); slider.setPaintTrack(true); 
			slider.setMajorTickSpacing(1);
			imagetf.setEditable(false);
			// "Movie" ���� ��
			if (choice == 1) {
				
				JPanel infoPanel = new JPanel();
				infoPanel.setLayout(new BorderLayout());
				Border movieBorder = BorderFactory.createTitledBorder("��ȭ ����");
				infoPanel.setBorder(movieBorder);
				JPanel moviePanel1 = new JPanel();
				JPanel moviePanel2 = new JPanel();
				GridLayout grid1 = new GridLayout(8,1);
				GridLayout grid2 = new GridLayout(2,1);
				grid1.setVgap(2); grid2.setVgap(1);
				moviePanel1.setLayout(grid1);
				moviePanel2.setLayout(grid2);
				// �̹��� �ҷ�����
				filebt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int ret = chooser.showOpenDialog(null);
						String pathName = chooser.getSelectedFile().getPath();
						imagetf.setText(pathName);
					}
				});
				if (bt == 2) { // ���� ��
					Item item = v.get(index);
					Movie m = (Movie)item;
					titletf.setText(m.getTitle()); creatortf.setText(m.getCreator()); 
					actortf.setText(m.getActor()); imagetf.setText(m.getImage()); 
					storyta.setText(m.getStory()); reviewta.setText(m.getReview()); 
					slider.setValue(m.getScore());
					genrecb.setSelectedItem(m.getGenre()); gradecb.setSelectedItem(m.getGrade()); 
					yearcb.setSelectedItem(m.getYear());
				}
				// OK ��ư ����
				okbt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(bt == 1) { // �߰� 

							String genre1 = (String)genrecb.getSelectedItem();
							String grade1 = (String)gradecb.getSelectedItem();
							String year1 = (String)yearcb.getSelectedItem();
							Movie movie1 = new Movie(titletf.getText(),creatortf.getText(),
									actortf.getText(),genre1,grade1,year1, imagetf.getText(), 
									slider.getValue(), storyta.getText(), reviewta.getText());
							ic.add(movie1);
							t1.add(titletf.getText());
							t2.add(titletf.getText());
							titletf.setText(""); creatortf.setText(""); actortf.setText(""); 
							imagetf.setText(""); storyta.setText(""); reviewta.setText(""); 
							slider.setValue(5);
							l1.setListData(t1); l2.setListData(t2);
						}
						else if(bt == 2) { // ����
							String genre2 = (String)genrecb.getSelectedItem();
							String grade2 = (String)gradecb.getSelectedItem();
							String year2 = (String)yearcb.getSelectedItem();
							Movie movie2 = new Movie(titletf.getText(),creatortf.getText(),
									actortf.getText(),genre2,grade2,year2, imagetf.getText(), 
									slider.getValue(), storyta.getText(), reviewta.getText());
							int n = t2.indexOf(v.get(index).getTitle());
							t2.set(n, titletf.getText());
							int eb = index;
							ic.edit(index, movie2);
							t1.set(index, titletf.getText());
							l1.setListData(t1); l2.setListData(t2);
							l1.setSelectedIndex(eb);
						}
						titletf.setText(""); creatortf.setText(""); actortf.setText(""); 
						imagetf.setText(""); 
						storyta.setText(""); reviewta.setText(""); slider.setValue(5);
						dialog.setVisible(false);
					}
					});
				JPanel okPanel = new JPanel();
				okPanel.add(okbt);
				JPanel imagePanel = new JPanel();
				imagePanel.add(imagetf);
				imagePanel.add(filebt);
				
				JPanel []panel = new JPanel[10];
				for(int i=0; i<10; i++) {
					panel[i] = new JPanel();
					if(i<8) 
						panel[i].setLayout(new GridLayout(1,2));
					else
						panel[i].setLayout(new FlowLayout(FlowLayout.RIGHT));
				}						
				panel[0].add(titlelb); panel[0].add(titletf);
				panel[1].add(creatorlb); panel[1].add(creatortf);
				panel[2].add(actorlb); panel[2].add(actortf);
				panel[3].add(genrelb); panel[3].add(genrecb);
				panel[4].add(gradelb); panel[4].add(gradecb);
				panel[5].add(yearlb); panel[5].add(yearcb);
				panel[6].add(imagelb); panel[6].add(imagePanel);
				panel[7].add(scorelb); panel[7].add(slider);
				panel[8].add(storylb); panel[8].add(new JScrollPane(storyta));
				panel[9].add(reviewlb); panel[9].add(new JScrollPane(reviewta));
				
				for(int i=0 ;i<8; i++)
					moviePanel1.add(panel[i]);
				moviePanel2.add(panel[8]); moviePanel2.add(panel[9]);
				
				infoPanel.add(moviePanel1, BorderLayout.NORTH);
				infoPanel.add(moviePanel2, BorderLayout.CENTER);
				infoPanel.add(okPanel, BorderLayout.SOUTH);
				add(infoPanel);
			    
			}
			// "Book" ���� ��
			else if(choice == 2) { 
				
				// ������Ʈ ����
				JPanel infoPanel = new JPanel();
				infoPanel.setLayout(new BorderLayout());
				Border bookBorder = BorderFactory.createTitledBorder("���� ����");
				infoPanel.setBorder(bookBorder);
				JPanel bookPanel1 = new JPanel();
				JPanel bookPanel2 = new JPanel();
				GridLayout grid1 = new GridLayout(6,1);
				GridLayout grid2 = new GridLayout(2,1);
				grid1.setVgap(2); grid2.setVgap(1); 
				bookPanel1.setLayout(grid1);
				bookPanel2.setLayout(grid2);
				// �̹��� �ҷ�����
				filebt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int ret = chooser.showOpenDialog(null);
						String pathName = chooser.getSelectedFile().getPath();
						imagetf.setText(pathName);
					}
				});
				
				JPanel []panel = new JPanel[8];
				for(int i=0; i<8; i++) {
					panel[i] = new JPanel();
					if(i<6) 
						panel[i].setLayout(new GridLayout(1,2));
					else
						panel[i].setLayout(new FlowLayout(FlowLayout.RIGHT));
				}	
				
				JPanel imagePanel = new JPanel();
				imagePanel.add(imagetf);
				imagePanel.add(filebt);
				if(bt == 2) { // ���� ��
					Item item = v.get(index);
					Book b = (Book)item;
					titletf.setText(b.getTitle()); creatortf.setText(b.getCreator()); 
					actortf.setText(b.getEditor()); imagetf.setText(b.getImage()); 
					storyta.setText(b.getStory()); reviewta.setText(b.getReview()); 
					slider.setValue(b.getScore()); yearcb.setSelectedItem(b.getYear());
				}
				// "OK" ��ư ����
				okbt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(bt == 1) { // �߰� ��
							
							String year1 = (String)yearcb.getSelectedItem();
							Book Book1 = new Book(titletf.getText(),creatortf.getText(),
									actortf.getText(),year1, imagetf.getText(), slider.getValue(), 
									storyta.getText(), reviewta.getText());
							ic.add(Book1);
							t1.add(titletf.getText());
							t3.add(titletf.getText());
							titletf.setText(""); creatortf.setText(""); actortf.setText(""); 
						    imagetf.setText(""); storyta.setText(""); reviewta.setText(""); 
							slider.setValue(5); 
							l1.setListData(t1); l3.setListData(t3);
						}
						else if(bt == 2) { // ���� ��
							String year2 = (String)yearcb.getSelectedItem();
							Book book2 = new Book(titletf.getText(),creatortf.getText(), 
									actortf.getText(), year2, imagetf.getText(), slider.getValue(), 
									storyta.getText(), reviewta.getText());
							int n = t3.indexOf(v.get(index).getTitle());
							t3.set(n, titletf.getText());
							int eb = index;
							ic.edit(index, book2);
							t1.set(index, titletf.getText());
							l1.setListData(t1); l3.setListData(t3);
							l1.setSelectedIndex(eb);
						}
						titletf.setText(""); creatortf.setText(""); actortf.setText(""); 
						imagetf.setText(""); storyta.setText(""); reviewta.setText(""); 
						slider.setValue(5); dialog.setVisible(false);
					}
				});
				JPanel okPanel = new JPanel();
				okPanel.add(okbt);
				
				creatorlb.setText("����"); actorlb.setText("���ǻ�"); yearlb.setText("���ǳ⵵"); 
				imagelb.setText("å�̹���"); storylb.setText("å�Ұ�"); 
				
				panel[0].add(titlelb); panel[0].add(titletf);
				panel[1].add(creatorlb); panel[1].add(creatortf);
				panel[2].add(actorlb); panel[2].add(actortf);
				panel[3].add(yearlb); panel[3].add(yearcb);
				panel[4].add(imagelb); panel[4].add(imagePanel);
				panel[5].add(scorelb); panel[5].add(slider);
				panel[6].add(storylb); panel[6].add(storyta);
				panel[7].add(reviewlb); panel[7].add(reviewta);
				
				for(int i=0 ;i<6; i++)
					bookPanel1.add(panel[i]);
				bookPanel2.add(panel[6]); bookPanel2.add(panel[7]);
				infoPanel.add(bookPanel1, BorderLayout.NORTH); infoPanel.add(bookPanel2, BorderLayout.CENTER);
				infoPanel.add(okPanel, BorderLayout.SOUTH);
			    add(infoPanel);
			}
		}
	}
	// �׸� ����
		class ListListener implements ListSelectionListener {

			public void valueChanged(ListSelectionEvent e) {
				// ��Ͽ��� ���õ� �ε���
				Object o = e.getSource();
				if(o.equals(l1)) {
					index = l1.getSelectedIndex();
					if(index != -1) { // ��Ͽ��� ���õ� �� �����ֱ�
						Item i = v.get(index);
						image = i.getImage();
						if(i.getClass().getName().equals("Movie")) { // Movie ���õ�
							c=1;
							cp.remove(p3);
							MyPanel3 mp3 = new MyPanel3();
							cp.add(mp3, BorderLayout.CENTER);
							cp.revalidate();
							p3=mp3;
						}
						else { // Book ���õ�
							c=2;
							cp.remove(p3);
							MyPanel3 mp3 = new MyPanel3();
							cp.add(mp3, BorderLayout.CENTER);
							cp.revalidate();
							p3=mp3;
						}
						l1.setSelectedIndex(-1);
					}
					else if(index == -1) { //����Ʈ ���õ��� �ʾ���
						c=3;
						cp.remove(p3);
						MyPanel3 mp3 = new MyPanel3();
						cp.add(mp3, BorderLayout.CENTER);
						cp.revalidate();
						p3=mp3;
					}
				}
				else if(o.equals(l2)) {
					index2 = l2.getSelectedIndex();
					if(index2 != -1) {
						String t = t2.get(index2);
						Object []arrItem  = v.toArray();
						for(int i=0; i<v.size(); i++) {
							Item item = (Item)arrItem[i];
							String title = item.getTitle();
							if(title.equals(t)) {
								index = i;
							}
						}
						Item im = v.get(index);
						image = im.getImage();
						c=1;
						cp.remove(p3);
						MyPanel3 mp3 = new MyPanel3();
						cp.add(mp3, BorderLayout.CENTER);
						cp.revalidate();
						p3=mp3;
						l2.setSelectedIndex(-1);
					}
					else if(index2 == -1) { //����Ʈ ���õ��� �ʾ���
						c=3;
						cp.remove(p3);
						MyPanel3 mp3 = new MyPanel3();
						cp.add(mp3, BorderLayout.CENTER);
						cp.revalidate();
						p3=mp3;
					}
				}
				else if(o.equals(l3)) {
					index3 = l3.getSelectedIndex();
					if(index3 != -1) {
						String t = t3.get(index3);
						Object []arrItem  = v.toArray();
						for(int i=0; i<v.size(); i++) {
							Item item = (Item)arrItem[i];
							String title = item.getTitle();
							if(title.equals(t)) {
								index = i;
							}
						}
						Item im = v.get(index);
						image = im.getImage();
						c=2;
						cp.remove(p3);
						MyPanel3 mp3 = new MyPanel3();
						cp.add(mp3, BorderLayout.CENTER);
						cp.revalidate();
						p3=mp3;
						l3.setSelectedIndex(-1);
					}
					else if(index3 == -1) { //����Ʈ ���õ��� �ʾ���
						c=3;
						cp.remove(p3);
						MyPanel3 mp3 = new MyPanel3();
						cp.add(mp3, BorderLayout.CENTER);
						cp.revalidate();
						p3=mp3;
					}
				}
				else if(o.equals(ls)) {
					index4 = ls.getSelectedIndex();
					if(index4 != -1) {
						String t = searchv.get(index4);
						Object []arrItem  = v.toArray();
						for(int i=0; i<v.size(); i++) {
							Item item = (Item)arrItem[i];
							String title = item.getTitle();
							if(title.equals(t)) {
								index = i;
							}
						}
						Item im = v.get(index);
						image = im.getImage();
						if(im.getClass().getName().equals("Movie")) {
							c=1;
						}
						else {
							c=2;
						}
						cp.remove(p3);
						MyPanel3 mp3 = new MyPanel3();
						cp.add(mp3, BorderLayout.CENTER);
						cp.revalidate();
						p3=mp3;
						ls.setSelectedIndex(-1);
					}
					else if(index4 == -1) { //����Ʈ ���õ��� �ʾ���
						c=3;
						cp.remove(p3);
						MyPanel3 mp3 = new MyPanel3();
						cp.add(mp3, BorderLayout.CENTER);
						cp.revalidate();
						p3=mp3;
					}
				}
				
			}
		}
	// �̹��� �׸���
	class ImagePanel extends JPanel {
		Image img;
		public ImagePanel() {
			if(c!=3) {
				ImageIcon icon = new ImageIcon(image);
				img = icon.getImage();
			}
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(c == 3)
				g.drawImage(null, 10, 10, 150, 200, this);
			else
				g.drawImage(img, 10, 10, 150, 200, this);
		}
	}
	// ������ (�󼼺���)
	class MyPanel3 extends JPanel {
		
		public MyPanel3() {
			// ������Ʈ ����
			setLayout(new BorderLayout());
			ImagePanel ip = new ImagePanel();
			JPanel storyPanel = new JPanel();
			JPanel reviewPanel = new JPanel();
			JPanel detailPanel = new JPanel();
			JPanel leftPanel = new JPanel();
			JPanel rightPanel = new JPanel();
			JTextArea story = new JTextArea(4, 54);
			JTextArea review = new JTextArea(4, 54);
			JLabel left[] = new JLabel[7]; 
			JLabel right[] = new JLabel[7];
			for(int i=0; i<7; i++) {
				left[i] = new JLabel("");
				right[i] = new JLabel("");
			}
			if(index != -1) {
				// "Movie" �׸� ���� ��
				if(c==1) {
					Movie m = (Movie)v.get(index);
					
					left[0].setText("����"); left[1].setText("����"); left[2].setText("���");
					left[3].setText("�帣"); left[4].setText("���"); left[5].setText("�����⵵");
					left[6].setText("����");
					right[0].setText(m.getTitle()); right[1].setText(m.getCreator()); right[2].setText(m.getActor());
					right[3].setText(m.getGenre()); right[4].setText(m.getGrade()); right[5].setText(m.getYear());
					right[6].setText(Integer.toString(m.getScore()));
					story.setText(m.getStory()); review.setText(m.getReview());
					GridLayout g = new GridLayout(7,1);
					leftPanel.setLayout(g); rightPanel.setLayout(g);
					for(int i=0; i<7; i++) {
						leftPanel.add(left[i]);
						rightPanel.add(right[i]);
					}
				}
				// "Book" �׸� ���� ��
				else if(c==2) {
					left[0].setText("����"); left[1].setText("����"); left[2].setText("���ǻ�"); left[3].setText("���ǳ⵵");
					left[4].setText("����");
					Book b = (Book)v.get(index);
					right[0].setText(b.getTitle()); right[1].setText(b.getCreator()); right[2].setText(b.getEditor());
					right[3].setText(b.getYear());right[4].setText(Integer.toString(b.getScore()));
					story.setText(b.getStory()); review.setText(b.getReview());
					GridLayout g = new GridLayout(5,1);
					leftPanel.setLayout(g); rightPanel.setLayout(g);
					for(int i=0; i<5; i++) {
						leftPanel.add(left[i]);
						rightPanel.add(right[i]);
					}
				}
			}
			// �ƹ��͵� ���� ���� ��
			else if(c==3) {
				for(int i=0; i<5; i++) {
					leftPanel.add(left[i]);
					rightPanel.add(right[i]);
				}
			}
			
			BorderLayout bl = new BorderLayout();
			bl.setHgap(10); 
			detailPanel.setLayout(bl);
			detailPanel.add(leftPanel, BorderLayout.WEST); detailPanel.add(rightPanel, BorderLayout.CENTER);
			Border storyBorder = BorderFactory.createTitledBorder("�ٰŸ�");
			storyPanel.setBorder(storyBorder);
			storyPanel.add(new JScrollPane(story));
			Border reviewBorder = BorderFactory.createTitledBorder("������");
			reviewPanel.setBorder(reviewBorder);
			reviewPanel.add(new JScrollPane(review));
			JPanel detail = new JPanel();
			Border detailBorder = BorderFactory.createTitledBorder("�� ����");
			detail.setBorder(detailBorder);
			detail.setLayout(new GridLayout(2,1));
			JPanel upPanel = new JPanel();
			GridLayout gl = new GridLayout();
			upPanel.setLayout(gl);
			upPanel.add(ip);
			upPanel.add(detailPanel);
			JPanel downPanel = new JPanel();
			downPanel.setLayout(new GridLayout(2,1));
			downPanel.add(storyPanel); downPanel.add(reviewPanel);
			detail.add(upPanel);
			detail.add(downPanel);
			JButton edit = new JButton("����"); // ���� ��ư
			edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					movierb.setSelected(true);
					bt = 2;
					dialog.remove(dp);
					if(v.get(index).getClass().getName().equals("Movie")) {
						choice = 1;
						DialogPanel movieDialog = new DialogPanel();
						dialog.add(movieDialog, BorderLayout.CENTER);
						dialog.revalidate();
						dp=movieDialog;
					}
					else {
						bookrb.setSelected(true);
						choice = 2;
						DialogPanel bookDialog = new DialogPanel();
						dialog.add(bookDialog, BorderLayout.CENTER);
						dialog.revalidate();
						dp=bookDialog;
					}
					dialog.setVisible(true);
				}
			});
			JButton remove = new JButton("����"); // ���� ��ư
			remove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "���� Ȯ��", 
							JOptionPane.YES_NO_OPTION);
					   if(result == JOptionPane.YES_OPTION) {
						   if(index != -1)  {
							   if(v.get(index).getClass().getName().equals("Movie")) {
								   int n = t2.indexOf(v.get(index).getTitle());
								   t2.remove(n);
								   l2.setListData(t2);
							   }
							   else {
								   int n = t3.indexOf(v.get(index).getTitle());
								   t3.remove(n);
								   l3.setListData(t3);
							   }
							   ic.remove(index);
							   t1.remove(index);
						   }
						   l1.setListData(t1);
					   }
				}
			});
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(edit);
			buttonPanel.add(remove);
			add(detail, BorderLayout.CENTER);
			add(buttonPanel, BorderLayout.SOUTH);
		}
	}	
				 
	public static void main(String[] args) {
		new MyNotes();
	}
}