package com.crayons_2_0.service.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentCourses;
import com.crayons_2_0.component.ImageUploadEditor;
import com.crayons_2_0.component.MultipleChoiceEditor;
import com.crayons_2_0.component.TextEditor;
import com.crayons_2_0.component.Unit;
import com.crayons_2_0.component.UnitPageLayout.WrappedPageItem;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.UnitData;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
public class UnitService {

	@Autowired
	private UnitDAO unitDAO;
	@Autowired
	private CourseService courseService;

	private List<Unit> findAll() {
		List<Unit> res = unitDAO.findAll();
		return res;
	}

	/**
	 * Returns all Units of Course
	 * 
	 * @param course
	 *            to find the Units of
	 * @return the Units of the course
	 */
	public List<Unit> findUnitsOfCourse(Course course) {
		List<Unit> allUnits = findAll();
		List<Unit> unitsOfCourse = new LinkedList<Unit>();

		// Alternativ mit SQL Fremdschlüssel
		for (Unit tmpUnit : allUnits) {
			if (tmpUnit.getCourseTitle().equals(course.getTitle())) {
				unitsOfCourse.add(tmpUnit);
			}

		}
		return unitsOfCourse;
	}

	public void saveUnitData(VerticalLayout layout, String titleUnit,
			String titleCourse) {

		File file = new File(titleUnit + ".bin");
		ObjectOutputStream out;
		WrappedPageItem c = null;
		ArrayList<UnitData> dataList = new ArrayList<UnitData>();

		for (int i = 0; i < layout.getComponentCount(); i++) {
			c = (WrappedPageItem) layout.getComponent(i);

			if (c.getContent().getClass().getName()
					.equals("com.crayons_2_0.component.TextEditor")) {
				TextEditor x = (TextEditor) c.getContent();
				dataList.add(dataList.size(), new UnitData(x.getContent()));
			}

			if (c.getContent().getClass().getName()
					.equals("com.crayons_2_0.component.MultipleChoiceEditor")) {
				MultipleChoiceEditor x = (MultipleChoiceEditor) c.getContent();
				dataList.add(dataList.size(), new UnitData(x.getQuestionText()
						.getValue(), x.getAnswerList(), x.getRightAnswer()));
			}

			if (c.getContent().getClass().getName()
					.equals("com.crayons_2_0.component.ImageUploadEditor")) {
				ImageUploadEditor x = (ImageUploadEditor) c.getContent();
				dataList.add(dataList.size(),
						new UnitData(x.getImage(), x.getImageTitle()));
			}
		}

		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(dataList);
			unitDAO.saveData(file, titleUnit, titleCourse);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<UnitData> getUnitData(String unitTitle, String courseTitle) {
		ObjectInputStream in;
		List<UnitData> layout = null;
		File file = new File(unitTitle + ".bin");
		try {
			unitDAO.getData(unitTitle, courseTitle);
			in = new ObjectInputStream(new FileInputStream(file));
			layout = (List<UnitData>) in.readObject();
			in.close();
			Files.delete(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return layout;
	}

	public void newUnit() {
		unitDAO.insertUnit(CurrentCourses.getInstance().getUnitTitle(),
				CurrentCourses.getInstance().getTitle());
	}

	public boolean hasData() {
		return unitDAO.hasData(CurrentCourses.getInstance().getUnitTitle(),
				CurrentCourses.getInstance().getTitle());
	}
}
