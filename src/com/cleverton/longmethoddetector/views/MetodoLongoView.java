package com.cleverton.longmethoddetector.views;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.cleverton.longmethoddetector.model.InformacoesMetodoModel;
import com.cleverton.longmethoddetector.model.MetodoLongoProviderModel;

public class MetodoLongoView extends ViewPart {
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.cleverton.longmethoddetector.views.MetodoLongoView";

	private TableViewer viewer;
	private MetodoLongoComparator comparator;

	public void createPartControl(Composite parent) {
		createViewer(parent);
		// set the sorter for the table
		comparator = new MetodoLongoComparator();
		viewer.setComparator(comparator);
	}

	/*public void name() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent arg0) {
				System.out.println("Linha: " + viewer.getTable().getSelectionIndex());
				/*File fileToOpen = new File("Person.java");

				String filePath = "..." ;
				final IFile inputFile = ResourcesPlugin.getWorkspace().getRoot().
						getFileForLocation(Path.fromOSString(filePath));
				if (inputFile != null) {
				    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				    IEditorPart openEditor = IDE.openEditor(page, inputFile);
				}
				Path path = new Path("C:/workspace/com.exemplo.helloworld/src/com/exemplo/helloworld/views/Person.java");
				IFile fileToBeOpened = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
				IEditorInput editorInput = new FileEditorInput(fileToBeOpened);
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
				try {
					page.openEditor(editorInput, "org.eclipse.ui.DefaultTextEdtior");
				} catch (PartInitException e) {
					e.printStackTrace();
				}
				/*
				int line = 10;
				if (openEditor instanceof ITextEditor) {
					ITextEditor textEditor = (ITextEditor) openEditor ;
					IDocument document= textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
					textEditor.selectAndReveal(document.getLineOffset(line - 1), document.getLineLength(line-1));
				}
			}
		});
	}*/

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		// get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(MetodoLongoProviderModel.getMetodosLongos());
		// make the selection available to other views
		getSite().setSelectionProvider(viewer);
		// define layout for the viewer
		viewer.getControl().setLayoutData(layoutViewer());
	}

	private GridData layoutViewer() {
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		return gridData;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	// create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Diret�rio", "M�todo", "Linha Inicial", "N� de Linhas" };
		int[] bounds = { 400, 200, 100, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				InformacoesMetodoModel p = (InformacoesMetodoModel) element;
				return p.getDiretorioDaClasse();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				InformacoesMetodoModel p = (InformacoesMetodoModel) element;
				return p.getNomeMetodo();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				InformacoesMetodoModel p = (InformacoesMetodoModel) element;
				return p.getLinhaInicial()+"";
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				InformacoesMetodoModel p = (InformacoesMetodoModel) element;
				return p.getNumeroLinhas()+"";
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		column.addSelectionListener(getSelectionAdapter(column, colNumber));
		return viewerColumn;
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column,
			final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(column);
				viewer.refresh();
			}
		};
		return selectionAdapter;
	}

	//Used to update the viewer from outsite
	public void refresh() {
		if (getViewer() == null) {
			try {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				if (page.findView(ID) != null) {
					page.hideView(page.findView(ID));
					page.showView(ID);
				}
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} /* else {
			viewer.refresh();
		} */
	} 

	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
