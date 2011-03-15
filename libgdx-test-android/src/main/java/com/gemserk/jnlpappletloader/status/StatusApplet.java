package com.gemserk.jnlpappletloader.status;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JApplet;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class StatusApplet extends JApplet {

	private static final long serialVersionUID = -4497910610619789813L;
	
	@Override
	public void init() {
		super.init();
		
		System.out.println("StatusApplet init() started!");
		
		String html = "<div align=\"center\">This applet shows that JNLP Applet Loader works correctly</div>";
		
		add(new JEditorPane("text/html", html) {
			private static final long serialVersionUID = -2344915756882112715L;
			{
				setEditable(false);
				
				setBackground(Color.GREEN);

				addHyperlinkListener(new HyperlinkListener() {

					public void hyperlinkUpdate(HyperlinkEvent e) {
						if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
							System.out.println(e.getURL());
							getAppletContext().showDocument(e.getURL());
						}
					}
				});

			}

		});
		System.out.println("StatusApplet loaded correctly!");
		repaint();
	}
	
	public void stop() {
		super.stop();
	}

	public void start() {
		super.start();
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
	}
	

}
