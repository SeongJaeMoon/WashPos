package washfriends.washpos.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import washfriends.washpos.App;
import washfriends.washpos.controller.MultiPanel;
import washfriends.washpos.controller.MyFont;
import washfriends.washpos.controller.TimeOut;
import washfriends.washpos.controller.UITheme;
import washfriends.washpos.controller.UIToolbox;
import washfriends.washpos.model.User;
import washfriends.washpos.uitool.HorizontalButton;
import washfriends.washpos.uitool.SquareButton;

/**
 * This class provides a view that displays
 * the main menu (home screen) for navigation
 * to other views.
 */
public class HomeView extends InitView {
	
	private TimeOut timeout = null;				// No reaction waiting time

	public HomeView() {
		super("HOMEVIEW", null, "메뉴를 선택하세요");
		App.LOGGER.info("Set Layout HomeView");
		JPanel inner = new JPanel();
		inner.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Line1
		JPanel line1 = new JPanel();
		c.fill = GridBagConstraints.PAGE_START;
		c.insets =  new Insets(0, 0, 0, 50); // top, left, bottom, right
		c.gridx = 0;
		c.gridy = 0;
		line1.setLayout(new BoxLayout(line1, BoxLayout.Y_AXIS));
		
		JButton btnWashShirt = new JButton("세탁");
		btnWashShirt.setFont(MyFont.REGULAR_FONT.deriveFont((float)25));
		btnWashShirt.setName("WASHSHIRT");
		btnWashShirt.setMargin(new Insets(10, 0, 10, 0));
		UIToolbox.setSize(btnWashShirt, new Dimension(300, 100));
		line1.add(btnWashShirt);
		line1.add(Box.createVerticalStrut(10));
		
		JButton btnDry = new JButton("건조");
		btnDry.setFont(MyFont.REGULAR_FONT.deriveFont((float)25));
		btnDry.setName("DRY");
		btnDry.setMargin(new Insets(10, 0, 10, 0));
		UIToolbox.setSize(btnDry, new Dimension(300, 100));
		line1.add(btnDry);
		line1.add(Box.createVerticalStrut(10));
		
		JButton btnBuy = new JButton("세탁용품 구입");
		btnBuy.setFont(MyFont.REGULAR_FONT.deriveFont((float)25));
		btnBuy.setName("BUY");
		btnBuy.setMargin(new Insets(10, 0, 10, 0));
		UIToolbox.setSize(btnBuy, new Dimension(300, 100));
		line1.add(btnBuy);
		line1.add(Box.createVerticalStrut(10));
		
		JButton btnWashShoe = new JButton("운동화 세탁");
		btnWashShoe.setFont(MyFont.REGULAR_FONT.deriveFont((float)25));
		btnWashShoe.setName("WASHSHOE");
		btnWashShoe.setMargin(new Insets(10, 0, 10, 0));
		UIToolbox.setSize(btnWashShoe, new Dimension(300, 100));
		line1.add(btnWashShoe);
		line1.add(Box.createVerticalStrut(10));
		
		JButton btnDryShoe = new JButton("운동화 건조");
		btnDryShoe.setFont(MyFont.REGULAR_FONT.deriveFont((float)25));
		btnDryShoe.setName("DRYSHOE");
		btnDryShoe.setMargin(new Insets(10, 0, 10, 0));
		UIToolbox.setSize(btnDryShoe, new Dimension(300, 100));
		line1.add(btnDryShoe);
		line1.add(Box.createVerticalStrut(10));
		inner.add(line1, c);
		
		App.USER = (App.USER != null) ? App.USER : new User("01012345678", "1234", 25, 25);
		
		// Line2
		JPanel line2 = new JPanel();
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 0;
		line2.setLayout(new BoxLayout(line2, BoxLayout.Y_AXIS));		
		line2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel label1 = new JLabel();
		label1.setFont(MyFont.REGULAR_FONT.deriveFont((float)50));
		label1.setText("<html>회원번호<br>" + App.USER.getEncryptPid() + "</html>");
		label1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		line2.add(label1);
		
		JLabel label2 = new JLabel();
		label2.setFont(MyFont.REGULAR_FONT.deriveFont((float)30));
		label2.setText("현금 잔액 " + App.USER.getCash());
		label2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		line2.add(label2);
		
		JLabel label3 = new JLabel();
		label3.setFont(MyFont.REGULAR_FONT.deriveFont((float)30));
		label3.setText("포인트 잔액 " + App.USER.getPoint());
		label3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		line2.add(label3);
		
		JLabel label4 = new JLabel();
		label4.setFont(MyFont.REGULAR_FONT.deriveFont((float)30));
		label4.setText("잔액 합계 " + App.USER.getPointAndCash());
		label4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		line2.add(label4);
		
		JLabel label5 = new JLabel();
		label5.setFont(MyFont.REGULAR_FONT.deriveFont((float)30));
		label5.setText("결제가능 잔액" + "");
		label5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		line2.add(label5);
		
		JLabel label6 = new JLabel();
		label6.setFont(MyFont.REGULAR_FONT.deriveFont((float)20));
		label6.setText("적립된 포인트(" + App.USER.getPoint() + ")\n 자동으로 결제됩니다");
		label6.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		line2.add(label6);
		line2.add(new HorizontalButton("HISTORY", "EDIT", "과거거래 보기", this));
		
		inner.add(line2, c);
		// Line3
		JPanel line3 = new JPanel();
		c.fill = GridBagConstraints.PAGE_END;
		c.insets =  new Insets(0, 50, 0, 0); // top, left, bottom, right
		c.gridx = 2;
		c.gridy = 0;
		line3.setLayout(new BoxLayout(line3, BoxLayout.Y_AXIS));
		
		JButton btnCash = new JButton("현금 충전");
		btnCash.setFont(MyFont.REGULAR_FONT.deriveFont((float)35));
		btnCash.setName("CASH");
		btnCash.setMargin(new Insets(10, 0, 10, 0));
		UIToolbox.setSize(btnCash, new Dimension(300, 100));
		line3.add(btnCash);
		line3.add(Box.createVerticalStrut(30));
		
		JButton btnStar = new JButton("트롬 스타");
		btnStar.setFont(MyFont.REGULAR_FONT.deriveFont((float)35));
		btnStar.setName("STAR");
		btnStar.setMargin(new Insets(10, 0, 10, 0));
		UIToolbox.setSize(btnStar, new Dimension(300, 100));
		line3.add(btnStar);
		line3.add(Box.createVerticalStrut(30));
		
		JButton btnPW = new JButton("비밀번호 변경");
		btnPW.setFont(MyFont.REGULAR_FONT.deriveFont((float)35));
		btnPW.setName("PASSWORD");
		btnPW.setMargin(new Insets(10, 0, 10, 0));
		UIToolbox.setSize(btnPW, new Dimension(300, 100));
		line3.add(btnPW);
		inner.add(line3, c);
		
		add(inner);
		
		JPanel nav = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nav.add(new HorizontalButton("EXIT", "EXIT", "<html>터치 - 즉시 로그아웃<br>60초 후 자동 로그아웃 됩니다.</html>", this, true));

        add(nav, BorderLayout.SOUTH);
	}
	@Override
    public boolean prepareView(Object... args) {
		if (timeout != null) {
            timeout.stop();
        }
		timeout = new TimeOut(new Runnable() {
            public void run() {
                MultiPanel.SELF.show("WELCOME");
            }
        }, 60 * 1000).start();
		return false;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
