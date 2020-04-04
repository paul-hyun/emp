package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne_group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonToggle;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.PanelPopup4NeSummary;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group.PanelPopup4NeGroupSummary;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.Panel4NetworkViewAt;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange.SIZE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode.ToolbarItemIf;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.COLOR_ORANGE;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.FONT_ORANGE;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.IMAGE_ORANGE;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Panel4NetworkViewNeGroup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4NetworkViewNeGroup extends Panel4NetworkViewAt {

	public interface Panel4NetworkViewNeGroupListenerIf extends Panel4NetworkViewListenerIf {

		public void createNetworkLink(Model4NetworkLink networkLink);

		public void deleteNetworkLink(int network_link_id);

	}

	/**
	 * 네트워크보기 모드 (노멀, 링크, 편집)
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public enum MAP_MODE {
		NORMAL, LINK, EDIT
	}

	public class Figure4Bg extends FreeformLayeredPane {

		/**
		 * 배경 이미지
		 */
		public Image backgroundImage;

		@Override
		public void paintFigure(Graphics graphics) {
			graphics.setBackgroundColor(getBackgroundColor());
			graphics.fillRectangle(getBounds());
			if (backgroundImage != null) {
				graphics.drawImage(backgroundImage, 0, 0);
			}
		}

		/**
		 * 배경이미지를 설정합니다.
		 * 
		 * @param backgroundImage
		 *            배경이미지
		 */
		public void setBackgroundImage(Image backgroundImage) {
			this.backgroundImage = backgroundImage;
		}

		/**
		 * 아이콘(노드)를 선택합니다.
		 * 
		 * @param figure
		 *            노드
		 */
		public void select(IFigure figure) {
			Panel4NetworkViewNeGroup.this.selectedNode = null;

			for (Object object : getChildren()) {
				if (object instanceof Figure4Node) {
					Figure4Node figureNode = (Figure4Node) object;
					figureNode.setSelected((object == figure));
					if (figureNode.isSelected()) {
						Panel4NetworkViewNeGroup.this.selectedNode = figureNode.node;
					}
				} else if (mapMode == MAP_MODE.LINK && object instanceof Figure4Link) {
					((Figure4Link) object).setSelected((object == figure));
				}
			}

			repaint();
		}

		/**
		 * 화면에 아이콘(노드)를 출력합니다.
		 * 
		 * @param node
		 *            노드
		 */
		public void display(NODE node) {
			try {
				Model4NeGroup neGroup = (Model4NeGroup) node.getValue();

				int r = 0xFF & neGroup.getNe_group_map_bg_color() >> 16;
				int g = 0xFF & neGroup.getNe_group_map_bg_color() >> 8;
				int b = 0xFF & neGroup.getNe_group_map_bg_color() >> 0;
				setBackgroundColor(UtilResource.getColor(r, g, b));

				if (neGroup.getNe_group_map_bg_image() != null && neGroup.getNe_group_map_bg_image().length() > 0) {
					setBackgroundImage(UtilResource4Orange.getRawImage(neGroup.getNe_group_map_bg_image()));
				} else {
					setBackgroundImage(null);
				}

				// OLD Figure 분류 및 저장
				Map<Integer, Figure4Node> oldNeGroupMap = new HashMap<Integer, Figure4Node>();
				Map<Integer, Figure4Node> oldNeMap = new HashMap<Integer, Figure4Node>();
				Map<Integer, Figure4Link> oldLinkMap = new HashMap<Integer, Figure4Link>();
				for (Object object : getChildren()) {
					if (object instanceof Figure4Node) {
						Figure4Node figureNode = (Figure4Node) object;
						NODE nnn = figureNode.getValue();
						if (nnn.isNeGroup()) {
							oldNeGroupMap.put(nnn.getId(), figureNode);
						} else if (nnn.isNe()) {
							oldNeMap.put(nnn.getId(), figureNode);
						}
					} else if (object instanceof Figure4Link) {
						Figure4Link figureLink = (Figure4Link) object;
						oldLinkMap.put(figureLink.getValue().getNetwork_link_id(), figureLink);
					}
				}

				// CHILD 생성 (존재하는 것은 skip)
				NODE[] childs = ModelClient4NetworkTree.getInstance().getListChild(neGroup.getNe_group_id());

				Map<Integer, Figure4Node> newNeGroupMap = new HashMap<Integer, Figure4Node>();
				Map<Integer, Figure4Node> newNeMap = new HashMap<Integer, Figure4Node>();
				for (int i = 0; i < childs.length; i++) {
					if (childs[i].isNeGroup()) {
						Figure4Node figureNode = oldNeGroupMap.remove(childs[i].getId());
						if (figureNode == null) {
							figureNode = createFigure4Node(childs[i]);
							add(figureNode);
						} else {
							figureNode.initFigure();
						}
						newNeGroupMap.put(childs[i].getId(), figureNode);
					} else if (childs[i].isNe()) {
						Figure4Node figureNode = oldNeMap.remove(childs[i].getId());
						if (figureNode == null) {
							figureNode = createFigure4Node(childs[i]);
							add(figureNode);
						} else {
							figureNode.initFigure();
						}
						newNeMap.put(childs[i].getId(), figureNode);
					}
				}

				// OLD중 존재하지 않는 것 삭제
				for (Figure4Node figureNode : oldNeGroupMap.values()) {
					remove(figureNode);
				}
				for (Figure4Node figureNode : oldNeMap.values()) {
					remove(figureNode);
				}

				// LINK 생성
				Model4NetworkLink[] network_links = ModelClient4NetworkTree.getInstance().getNetworkLinks();

				for (Model4NetworkLink network_link : network_links) {
					Figure4Node src = null;
					if (network_link.getNe_group_id_from() != 0) {
						src = newNeGroupMap.get(network_link.getNe_group_id_from());
					} else if (network_link.getNe_id_from() != 0) {
						src = newNeMap.get(network_link.getNe_id_from());
					}

					Figure4Node dst = null;
					if (network_link.getNe_group_id_to() != 0) {
						dst = newNeGroupMap.get(network_link.getNe_group_id_to());
					} else if (network_link.getNe_id_to() != 0) {
						dst = newNeMap.get(network_link.getNe_id_to());
					}

					Figure4Link figureLink = oldLinkMap.remove(network_link.getNetwork_link_id());
					if (src != null && dst != null && figureLink == null) {
						figureLink = new Figure4Link(network_link);
						figureLink.setSourceAnchor(src.getAnchor());
						figureLink.setTargetAnchor(dst.getAnchor());
						add(figureLink, 0);
					} else if ((src == null || dst == null) && figureLink != null) {
						remove(figureLink);
					}
				}

				// OLDE LINK 삭제
				for (Figure4Link figureLink : oldLinkMap.values()) {
					remove(figureLink);
				}

				if (mapMode == MAP_MODE.NORMAL) {
					layoutNodes();
					computeScroll(computeSize());
				}
				Figure4Bg.this.repaint();
			} catch (EmpException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 노드의 레이아웃을 설정합니다.
		 */
		public void layoutNodes() {
			List<Figure4Node> fixedNodeList = new ArrayList<Figure4Node>();
			List<Figure4Node> movedNodeList = new ArrayList<Figure4Node>();

			for (Object object : getChildren()) {
				if (object instanceof Figure4Node) {
					Figure4Node figureNode = (Figure4Node) object;
					figureNode.computeBounds();
					if (figureNode.getLocation().x == 0 && figureNode.getLocation().y == 0) {
						movedNodeList.add(figureNode);
					} else {
						fixedNodeList.add(figureNode);
					}
				}
			}

			int x = 0;
			int y = 0;
			int length = 1;

			for (Figure4Node movedNode : movedNodeList) {
				Rectangle rect = movedNode.getBounds().getCopy();

				while (true) {
					rect.x = 20 + x * 100;
					rect.y = 20 + y * 100;

					if (x == y) {
						x++;
						y = 0;
						length++;
					} else if (y < x) {
						x = (y == length - 2) ? 0 : length - 1;
						y++;
					} else {
						x++;
					}

					boolean intersect = false;
					for (Figure4Node fixedNode : fixedNodeList) {
						if (rect.intersects(fixedNode.getBounds())) {
							intersect = true;
							break;
						}
					}

					if (!intersect) {
						movedNode.setLocation(new Point(rect.x, rect.y));
						fixedNodeList.add(movedNode);
						break;
					}
				}
			}
		}

		/**
		 * 깜빡임 상태를 설정합니다.
		 * 
		 * @param blink
		 *            깜빡임 상태
		 */
		public void blink(boolean blink) {
			int repaint_count = 0;
			for (Object object : getChildren()) {
				if (object instanceof Figure4Node) {
					Figure4Node figure4Node = (Figure4Node) object;
					if (figure4Node.isNeedBlink() || (!blink && figure4Node.isBlink())) {
						figure4Node.blink(blink);
						repaint_count++;
					}
				}
			}

			if (0 < repaint_count) {
				Figure4Bg.this.repaint();
			}
		}

		/**
		 * 화면의 크기를 계산합니다.
		 * 
		 * @return 화면크기
		 */
		public org.eclipse.swt.graphics.Point computeSize() {
			org.eclipse.swt.graphics.Point point = new org.eclipse.swt.graphics.Point(256, 256);
			for (Object object : getChildren()) {
				IFigure figure = (IFigure) object;
				Rectangle rect = figure.getBounds();
				point.x = Math.max(point.x, rect.x + rect.width + 20);
				point.y = Math.max(point.y, rect.y + rect.height + 20);
			}
			return point;
		}

	}

	public class Figure4Node extends Figure {

		/**
		 * 링크 선과 아이콘의 테두리를 연결하는 앵커
		 */
		public ChopboxAnchor anchor;

		/**
		 * 노드
		 */
		public NODE node;

		/**
		 * 텍스트
		 */
		public String text;

		/**
		 * 이미지
		 */
		public Image image;

		/**
		 * 상태 이미지 배열
		 */
		public Image[] imageStatuss;

		/**
		 * 선택상태
		 */
		public boolean selected;

		/**
		 * 아이콘 깜빡임 상태
		 */
		public boolean icon_blink;

		/**
		 * 생성자 입니다.
		 * 
		 * @param node
		 *            노드
		 */
		public Figure4Node(NODE node) {
			this.anchor = new ChopboxAnchor(this);
			this.node = node;

			initFigure();
		}

		/**
		 * 초기화 합니다.
		 */
		public void initFigure() {
			this.text = node.getName();
			this.image = UtilResource4Orange.getNetworkMapIcon(node.getIcon(), node.getSeverity());
			this.imageStatuss = getNetworkMapNeSessionIcons(node.isNe() ? ((Model4Ne) node.getValue()).getNeSessions() : new Model4NeSessionIf[] {});
		}

		@Override
		public void paintFigure(Graphics graphics) {
			Rectangle bounds = getBounds();
			Rectangle border = new Rectangle(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);

			int y = bounds.y;
			if (image != null) {
				if (!icon_blink) {
					int startX = bounds.x + (bounds.width - SIZE_ORANGE.NETWORK_NESESSION_ICON.size() - 2 - image.getBounds().width) / 2;
					int startY = bounds.y + 4;

					for (Image imageStatus : imageStatuss) {
						if (imageStatus != null) {
							graphics.drawImage(imageStatus, new Point(startX, startY));
						}
						startY += (SIZE_ORANGE.NETWORK_NESESSION_ICON.size() + 2);
					}

					graphics.drawImage(image, new Point(startX + SIZE_ORANGE.NETWORK_NESESSION_ICON.size() + 2, y + 4));
				}
				y += (4 + image.getBounds().height);
			}
			if (text != null) {
				graphics.setFont(NODE_FONT);
				Dimension size = FigureUtilities.getTextExtents(text, NODE_FONT);
				graphics.drawText(text, bounds.x + (bounds.width - size.width) / 2, y + 8);
			}

			graphics.setForegroundColor(selected ? NODE_SELECTED : NODE_UNSELECTED);
			graphics.drawRoundRectangle(border, 8, 8);
		}

		public void dispose() {
			IFigure parent = getParent();
			if (parent != null) {
				parent.remove(this);
			}
		}

		/**
		 * 앵커를 반환합니다.
		 * 
		 * @return 앵커
		 */
		public ConnectionAnchor getAnchor() {
			return anchor;
		}

		/**
		 * 노드를 반환합니다.
		 * 
		 * @return 노드
		 */
		public NODE getValue() {
			return node;
		}

		/**
		 * 노드를 설정합니다.
		 * 
		 * @param node
		 *            노드
		 */
		public void setValue(NODE node) {
			this.node = node;
		}

		/**
		 * 선택상태를 반환합니다.
		 * 
		 * @return 선택상태
		 */
		public boolean isSelected() {
			return selected;
		}

		/**
		 * 선택상태를 설정합니다.
		 * 
		 * @param selected
		 *            선택상태
		 */
		public void setSelected(boolean selected) {
			this.selected = selected;
			if (selected && node != null) {
				node.stopIconBlink(false);
				this.icon_blink = false;
			}
		}

		/**
		 * 노드의 깜빡임 상태를 반환합니다.
		 * 
		 * @return 깜빡임 상태
		 */
		public boolean isNeedBlink() {
			return (node != null && node.isIconBlink());
		}

		/**
		 * 깜빡임 상태를 설정합니다.
		 * 
		 * @param blink
		 *            깜빡임 상태
		 */
		public void blink(boolean blink) {
			this.icon_blink = blink;
		}

		/**
		 * 깜빡임 상태를 반환합니다.
		 * 
		 * @return 깜빡임 상태
		 */
		public boolean isBlink() {
			return icon_blink;
		}

		/**
		 * 화면크기를 계산합니다.
		 */
		public void computeBounds() {
			int x = node.getMap_location_x();
			int y = node.getMap_location_y();

			Dimension minSize = new Dimension(8 + (SIZE_ORANGE.NETWORK_NESESSION_ICON.size() + 2), 8);
			if (image != null) {
				org.eclipse.swt.graphics.Rectangle imageRect = image.getBounds();
				int expandWidth = Math.max(imageRect.width + 8 + (SIZE_ORANGE.NETWORK_NESESSION_ICON.size() + 2) - minSize.width, 0);
				minSize.expand(expandWidth, imageRect.height + 4);
			}
			if (text != null) {
				Dimension textSize = FigureUtilities.getTextExtents(text, NODE_FONT);
				int expandWidth = Math.max(textSize.width + 8 - minSize.width, 0);
				minSize.expand(expandWidth, textSize.height + 4);
			}

			setBounds(new Rectangle(x, y, minSize.width, minSize.height));
		}

		/**
		 * 아이콘의 이미지를 반환합니다.
		 * 
		 * @param neSessions
		 *            NE 세션 모델 배열
		 * @return 이미지 배열
		 */
		public Image[] getNetworkMapNeSessionIcons(Model4NeSessionIf[] neSessions) {
			Image[] images = new Image[neSessions.length];
			for (int i = 0; i < neSessions.length; i++) {
				if (neSessions[i] == null) {
				} else if (neSessions[i].getProtocol().equals(Model4NeSessionICMP.PROTOCOL)) {
					images[i] = neSessions[i].isAdmin_state() ? (neSessions[i].isNe_session_state() ? UtilResource4Orange.getRawImage("/data/image/ne_session_icon/ne_session_status_icmp_ok.png") : UtilResource4Orange.getRawImage("/data/image/ne_session_icon/ne_session_status_icmp_fail.png")) : null;
				} else if (neSessions[i].getProtocol().equals(Model4NeSessionSNMP.PROTOCOL)) {
					images[i] = neSessions[i].isAdmin_state() ? (neSessions[i].isNe_session_state() ? UtilResource4Orange.getRawImage("/data/image/ne_session_icon/ne_session_status_snmp_ok.png") : UtilResource4Orange.getRawImage("/data/image/ne_session_icon/ne_session_status_snmp_fail.png")) : null;
				}
			}
			return images;
		}

	}

	protected class Figure4Link extends PolylineConnection {

		/**
		 * 네트워크 링크 모델
		 */
		public Model4NetworkLink value;

		/**
		 * 선택상태
		 */
		public boolean selected;

		/**
		 * 생성자 입니다.
		 * 
		 * @param value
		 *            네트워크 링크 모델
		 */
		public Figure4Link(Model4NetworkLink value) {
			this.value = value;
			super.setLineWidth(2);
		}

		/**
		 * 선택상태를 반환합니다.
		 * 
		 * @return 선택상태
		 */
		public boolean isSelected() {
			return selected;
		}

		/**
		 * 선택상태를 설정합니다.
		 * 
		 * @param selected
		 *            선택상태
		 */
		public void setSelected(boolean selected) {
			this.selected = selected;

			setForegroundColor(selected ? UtilResource.getColor(255, 0, 0) : UtilResource.getColor(0, 0, 0));
		}

		/**
		 * 네트워크 링크 모델을 반환합니다.
		 * 
		 * @return 네트워크 링크 모델
		 */
		public Model4NetworkLink getValue() {
			return value;
		}

	}

	/**
	 * 네트워크보기 마우스 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class NetworkViewMouseListener implements //
			MouseListener, //
			MouseMoveListener, //
			KeyListener {

		/**
		 * 드래그 노드
		 */
		public Figure4Node dragNode;

		/**
		 * 드래그 옵셋
		 */
		public Point dragOffset = new Point();

		/**
		 * 링크
		 */
		public Figure figureLink = new Figure();

		/**
		 * 링크 연결선
		 */
		public PolylineConnection linkConnection = new PolylineConnection();

		@Override
		public void keyPressed(KeyEvent e) {
			if (mapMode == MAP_MODE.LINK && e.keyCode == SWT.DEL) {
				Figure4Link selectedLink = null;
				for (Object object : figure4Bg.getChildren()) {
					if (object instanceof Figure4Link && ((Figure4Link) object).isSelected()) {
						selectedLink = (Figure4Link) object;
						break;
					}
				}
				if (selectedLink != null && selectedLink.getValue() != null && DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.LINK), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_CONFIRM, MESSAGE_CODE_ORANGE.LINK, ""))) {
					((Panel4NetworkViewNeGroupListenerIf) listener).deleteNetworkLink(((Model4NetworkLink) selectedLink.getValue()).getNetwork_link_id());
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void mouseMove(MouseEvent e) {
			if (mapMode == MAP_MODE.EDIT && dragNode != null) {
				int x = Math.max(e.x - dragOffset.x, 4);
				int y = Math.max(e.y - dragOffset.y, 4);
				dragNode.setLocation(new Point(x, y));

				computeScroll(figure4Bg.computeSize());
				figure4Bg.repaint();
			} else if (mapMode == MAP_MODE.LINK && figureLink.getParent() == figure4Bg) {
				figureLink.setLocation(new Point(e.x, e.y));
			}
		}

		@Override
		public void mouseDoubleClick(MouseEvent e) {
			if (mapMode == MAP_MODE.NORMAL) {
				IFigure figure = figure4Bg.findFigureAt(e.x, e.y);
				if (e.button == 1 && figure instanceof Figure4Node) {
					listener.openNetworkView(((Figure4Node) figure).getValue());
					listener.selectViewNode(((Figure4Node) figure).getValue());
				}
			}
			clearDrag();
		}

		@Override
		public void mouseDown(MouseEvent e) {
			IFigure figure = figure4Bg.findFigureAt(e.x, e.y);
			figure4Bg.select(figure);

			if (e.button == 1 && e.count == 1 && figure instanceof Figure4Node) {
				if (mapMode == MAP_MODE.NORMAL) {
					Figure4Node figureNode = (Figure4Node) figure;
					PanelPopup popupPanel = Panel4NetworkViewNeGroup.this.createPanelPopup(Panel4NetworkViewNeGroup.this.getShell(), figureNode.getValue());
					if (popupPanel != null) {
						Rectangle nodeRect = figureNode.getBounds();
						org.eclipse.swt.graphics.Point locationPopup = Panel4NetworkViewNeGroup.this.canvas.toDisplay(nodeRect.x, nodeRect.y + nodeRect.height);

						org.eclipse.swt.graphics.Rectangle displayRect = Panel4NetworkViewNeGroup.this.getDisplay().getBounds();
						org.eclipse.swt.graphics.Point sizePopup = popupPanel.getSize();

						if (displayRect.width < locationPopup.x + sizePopup.x + 10) {
							locationPopup.x -= (sizePopup.x - nodeRect.width);
						}
						if (displayRect.height < locationPopup.y + sizePopup.y + 10) {
							locationPopup.y -= (sizePopup.y + nodeRect.height);
						}

						popupPanel.setLocation(locationPopup);
						// popupPanel.open();
					}
				} else if (mapMode == MAP_MODE.EDIT) {
					dragNode = (Figure4Node) figure;
					dragOffset.x = e.x - dragNode.getBounds().x;
					dragOffset.y = e.y - dragNode.getBounds().y;
				} else if (mapMode == MAP_MODE.LINK) {
					figureLink.setLocation(new Point(e.x, e.y));
					figure4Bg.add(figureLink);

					linkConnection.setSourceAnchor(new ChopboxAnchor(figure));
					linkConnection.setTargetAnchor(new ChopboxAnchor(figureLink));
					figure4Bg.add(linkConnection, 0);
				}
			} else {
				clearDrag();
			}
		}

		@Override
		public void mouseUp(MouseEvent e) {
			if (mapMode == MAP_MODE.LINK) {
				IFigure figure = figure4Bg.findFigureAt(e.x, e.y);
				if (figureLink != null && figureLink.getParent() == figure4Bg && linkConnection.getSourceAnchor().getOwner() instanceof Figure4Node && figure instanceof Figure4Node) {
					NODE fromNode = ((Figure4Node) linkConnection.getSourceAnchor().getOwner()).getValue();
					NODE toNode = ((Figure4Node) figure).getValue();
					if (fromNode != toNode) {
						if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.LINK), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_CONFIRM, MESSAGE_CODE_ORANGE.LINK, UtilString.format("{} -> {}", fromNode.getName(), toNode.getName())))) {
							Model4NetworkLink networkLink = new Model4NetworkLink();
							if (fromNode.isNeGroup() && toNode.isNeGroup()) {
								networkLink.setNe_group_id_from(Math.min(fromNode.getId(), toNode.getId()));
								networkLink.setNe_group_id_to(Math.max(fromNode.getId(), toNode.getId()));
							} else if (fromNode.isNeGroup() && toNode.isNe()) {
								networkLink.setNe_group_id_from(fromNode.getId());
								networkLink.setNe_id_to(toNode.getId());
							} else if (fromNode.isNe() && toNode.isNeGroup()) {
								networkLink.setNe_group_id_from(toNode.getId());
								networkLink.setNe_id_to(fromNode.getId());
							} else if (fromNode.isNe() && toNode.isNe()) {
								networkLink.setNe_id_from(Math.min(fromNode.getId(), toNode.getId()));
								networkLink.setNe_id_to(Math.max(fromNode.getId(), toNode.getId()));
							}
							((Panel4NetworkViewNeGroupListenerIf) listener).createNetworkLink(networkLink);
						}
					}
				}
			}

			clearDrag();
		}

		/**
		 * 드래그를 클리어 합니다.
		 */
		public void clearDrag() {
			dragNode = null;

			if (figureLink.getParent() == figure4Bg) {
				figure4Bg.remove(figureLink);
			}

			if (linkConnection.getParent() == figure4Bg) {
				figure4Bg.remove(linkConnection);
			}
		}

	}

	public class ToolbarItem4Link implements ToolbarItemIf {

		protected ButtonToggle buttonToggleLink;

		@Override
		public String getId() {
			return Panel4NetworkViewNeGroup.class.getSimpleName() + "LINK";
		}

		@Override
		public Control initControl(Composite parent) {
			buttonToggleLink = new ButtonToggle(parent, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_LINK_NORMAL), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_LINK_OVER), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_LINK_DOWN), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_LINK_DISABLED));
			buttonToggleLink.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Panel4NetworkViewNeGroup.this.setMapMode(buttonToggleLink.isSelection() ? MAP_MODE.LINK : MAP_MODE.NORMAL);
				}
			});
			buttonToggleLink.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LINK));
			buttonToggleLink.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LINK));
			return buttonToggleLink;
		}

		@Override
		public Control getControl() {
			return buttonToggleLink;
		}

		@Override
		public void setSelection(boolean selection) {
			if (buttonToggleLink != null) {
				buttonToggleLink.setSelection(selection);
			}
		}

		@Override
		public void setEnabled(boolean enabled) {
			if (buttonToggleLink != null) {
				buttonToggleLink.setEnabled(enabled);
			}
		}

	}

	public class ToolbarItem4Edit implements ToolbarItemIf {

		protected ButtonToggle buttonToggleEdit;

		@Override
		public String getId() {
			return Panel4NetworkViewNeGroup.class.getSimpleName() + "EDIT";
		}

		@Override
		public Control initControl(Composite parent) {
			buttonToggleEdit = new ButtonToggle(parent, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_NORMAL), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_OVER), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_DOWN), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_DISABLED));
			buttonToggleEdit.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Panel4NetworkViewNeGroup.this.setMapMode(buttonToggleEdit.isSelection() ? MAP_MODE.EDIT : MAP_MODE.NORMAL);
				}
			});
			buttonToggleEdit.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EDIT));
			buttonToggleEdit.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EDIT));
			return buttonToggleEdit;
		}

		@Override
		public Control getControl() {
			return buttonToggleEdit;
		}

		@Override
		public void setSelection(boolean selection) {
			if (buttonToggleEdit != null) {
				buttonToggleEdit.setSelection(selection);
			}
		}

		@Override
		public void setEnabled(boolean enabled) {
			if (buttonToggleEdit != null) {
				buttonToggleEdit.setEnabled(enabled);
			}
		}

	}

	public class ToolbarItem4Save implements ToolbarItemIf {

		protected ButtonImage buttonImageSave;

		@Override
		public String getId() {
			return Panel4NetworkViewNeGroup.class.getSimpleName() + "SAVE";
		}

		@Override
		public Control initControl(Composite parent) {
			buttonImageSave = new ButtonImage(parent, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_NORMAL), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_OVER), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_DOWN), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_DISABLED));
			buttonImageSave.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Panel4NetworkViewNeGroup.this.setMapMode(MAP_MODE.NORMAL);
				}
			});
			buttonImageSave.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SAVE));
			buttonImageSave.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SAVE));
			return buttonImageSave;
		}

		@Override
		public Control getControl() {
			return buttonImageSave;
		}

		@Override
		public void setSelection(boolean selection) {
		}

		@Override
		public void setEnabled(boolean enabled) {
			if (buttonImageSave != null) {
				buttonImageSave.setEnabled(enabled);
			}
		}

	}

	/**
	 * 노드 폰트
	 */
	protected static final Font NODE_FONT = ThemeFactory.getFont(FONT_ORANGE.NETWORK_MAP_NODE_NAME);

	/**
	 * 선택된 노드 색
	 */
	protected static final Color NODE_SELECTED = ThemeFactory.getColor(COLOR_ORANGE.NETWORK_ICON_SELECTED_BORDER);

	/**
	 * 선택되지 않은 노드 색
	 */
	protected static final Color NODE_UNSELECTED = ThemeFactory.getColor(COLOR_ORANGE.NETWORK_ICON_UNSELECTED_BORDER);

	/**
	 * 캔버스
	 */
	protected Canvas canvas;

	/**
	 * Draw2D에서 사용하는 GC 객체 같은 것
	 */
	protected LightweightSystem lightweightSystem;

	/**
	 * 아이콘 배경
	 */
	protected Figure4Bg figure4Bg;

	/**
	 * 노드
	 */
	protected NODE node;

	/**
	 * 선택된 노드
	 */
	protected NODE selectedNode;

	protected ToolbarItem4Link toolbarItem4Link;

	protected ToolbarItem4Edit toolbarItem4Edit;

	protected ToolbarItem4Save toolbarItem4Save;

	/**
	 * 네트워크 보기 모드
	 */
	protected MAP_MODE mapMode = MAP_MODE.NORMAL;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 */
	public Panel4NetworkViewNeGroup(Composite parent, int style, Panel4NetworkViewNeGroupListenerIf listener) {
		super(parent, style, listener);
	}

	@Override
	protected Control createContent(Composite parent) {
		canvas = new Canvas(parent, SWT.NONE);
		NetworkViewMouseListener networkViewMouseListener = new NetworkViewMouseListener();
		canvas.addMouseListener(networkViewMouseListener);
		canvas.addMouseMoveListener(networkViewMouseListener);
		canvas.addKeyListener(networkViewMouseListener);

		lightweightSystem = new LightweightSystem(canvas);
		figure4Bg = createFigure4Bg();
		figure4Bg.setBackgroundColor(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));
		lightweightSystem.setContents(figure4Bg);

		return canvas;
	}

	protected Figure4Bg createFigure4Bg() {
		return new Figure4Bg();
	}

	protected Figure4Node createFigure4Node(NODE node) {
		return new Figure4Node(node);
	}

	@Override
	protected boolean isShowPopupMenu() {
		return mapMode == MAP_MODE.NORMAL;
	}

	@Override
	protected NODE getNode() {
		return node;
	}

	@Override
	protected NODE getSelectedNode() {
		return selectedNode != null ? selectedNode : node;
	}

	@Override
	public void display(boolean progressBar) {
		figure4Bg.display(node);
	}

	@Override
	public void display(NODE node, boolean progressBar) {
		if (this.node == null || !this.node.isNeGroup() || !node.isNeGroup() || this.node.getId() != node.getId()) {
			setMapMode(MAP_MODE.NORMAL);
		}

		if (node.isNeGroup()) {
			this.node = node;
			display(progressBar);
		}
	}

	@Override
	public void display(NODE node, Object object) {
	}

	@Override
	public void monitor() {
		if (!isDisposed() && node != null && node.isNeGroup()) {
			if (Thread.currentThread() == getDisplay().getThread()) {
				display(false);
			} else {
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						display(false);
					}
				});
			}
		}
	}

	@Override
	public void blink(final boolean blink) {
		if (mapMode == MAP_MODE.NORMAL) {
			if (Thread.currentThread() == getDisplay().getThread()) {
				figure4Bg.blink(blink);
			} else {
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						figure4Bg.blink(blink);
					}
				});
			}
		}
	}

	@Override
	protected ToolbarItemIf[] getToolbarItems() {
		if (toolbarItem4Link == null) {
			toolbarItem4Link = new ToolbarItem4Link();
		}
		if (toolbarItem4Edit == null) {
			toolbarItem4Edit = new ToolbarItem4Edit();
		}
		if (toolbarItem4Save == null) {
			toolbarItem4Save = new ToolbarItem4Save();
		}
		return new ToolbarItemIf[] { toolbarItem4Link, toolbarItem4Edit, toolbarItem4Save };
	}

	/**
	 * 팝업 판넬을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param node
	 *            노드
	 * @return 팝업 판넬
	 */
	protected PanelPopup createPanelPopup(Shell parent, NODE node) {
		if (node.isNeGroup()) {
			return new PanelPopup4NeGroupSummary(parent, node);
		} else if (node.isNe()) {
			return new PanelPopup4NeSummary(parent, node);
		} else {
			return null;
		}
	}

	protected void setMapMode(MAP_MODE mapMode) {
		if (toolbarItem4Link != null && toolbarItem4Edit != null && toolbarItem4Save != null) {
			toolbarItem4Link.setSelection(mapMode == MAP_MODE.LINK);
			toolbarItem4Edit.setSelection(mapMode == MAP_MODE.EDIT);
			toolbarItem4Save.setEnabled(mapMode == MAP_MODE.EDIT);
		}

		if (this.mapMode == MAP_MODE.EDIT && mapMode != MAP_MODE.EDIT) {
			updateMapLocation();
		}
		this.mapMode = mapMode;

		if (mapMode == MAP_MODE.NORMAL) {
			figure4Bg.layoutNodes();
		} else {
			figure4Bg.blink(false);
		}
	}

	protected void updateMapLocation() {
		List<Object> valueList = new ArrayList<Object>();

		for (Object object : figure4Bg.getChildren()) {
			if (object instanceof Figure4Node) {
				Figure4Node figureNode = (Figure4Node) object;

				if (figureNode.getValue().getValue() instanceof Model4NeGroup) {
					Model4NeGroup neGroup = ((Model4NeGroup) figureNode.getValue().getValue()).copy();
					neGroup.setNe_group_map_location_x(figureNode.getBounds().x);
					neGroup.setNe_group_map_location_y(figureNode.getBounds().y);

					valueList.add(neGroup);
				} else if (figureNode.getValue().getValue() instanceof Model4Ne) {
					Model4Ne ne = ((Model4Ne) figureNode.getValue().getValue()).copy();

					ne.setNe_map_location_x(figureNode.getBounds().x);
					ne.setNe_map_location_y(figureNode.getBounds().y);

					valueList.add(ne);
				}
			}
		}

		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_MAP_LOCATION), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_CONFIRM_MAP_LOCATION))) {
			listener.updateMapLocation(valueList.toArray(new ModelIf[0]));
		}
	}

}
