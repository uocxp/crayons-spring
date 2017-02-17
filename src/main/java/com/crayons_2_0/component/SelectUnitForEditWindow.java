package com.crayons_2_0.component;

import java.util.ResourceBundle;

import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.view.Uniteditor;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class SelectUnitForEditWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Graph graph;
	
	private ResourceBundle lang = LanguageService.getInstance().getRes();

	public SelectUnitForEditWindow(Graph graphData) {
		graph = graphData;
		setSizeFull();
		setModal(true);
		setResizable(false);
		setClosable(true);
		setHeight(40.0f, Unit.PERCENTAGE);
		setWidth(40.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(true);
		setContent(content);

		Component title = buildTitle();
		content.addComponent(title);
		content.setComponentAlignment(title, Alignment.TOP_CENTER);

		// content.addComponent(buildDescription());

		Component unitChoise = buildUnitChoice();
		content.addComponent(unitChoise);
		content.setComponentAlignment(unitChoise, Alignment.MIDDLE_LEFT);

		Component footer = buildFooter();
		content.addComponent(footer);
		content.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
	}

	private Component buildUnitChoice() {
		ComboBox selectUnit = new ComboBox(lang.getString("SelectTheUnitForEdit"));
		for (UnitNode tmp : graph.getUnitCollection()) {
			selectUnit.addItem(tmp.getUnitNodeTitle());
		}
		return selectUnit;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button(lang.getString("Edit"));
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				close();
				UI.getCurrent().getNavigator().navigateTo(Uniteditor.VIEW_NAME);

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_CENTER);
		return footer;
	}

	private Component buildTitle() {
		Label title = new Label(lang.getString("OpenTheUnitEditor"));
		title.addStyleName(ValoTheme.LABEL_H2);
		return title;
	}

	public static void refreshData(Graph graphData) {
		graph = graphData;

	}

	/*
	 * private Component buildDescription() { return null; }
	 */
}
