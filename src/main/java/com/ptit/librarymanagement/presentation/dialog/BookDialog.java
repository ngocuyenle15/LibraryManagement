package com.ptit.librarymanagement.presentation.dialog;

import com.ptit.librarymanagement.model.dto.*;
import com.ptit.librarymanagement.presentation.dialog.component.*;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;


public final class BookDialog extends JDialog {
    private HeaderTitle titlePanel;
    private JPanel bookFormPanel, buttonPanel, centerPanel, coverPanel, mainPanel;
    private ButtonCustom createButton, cancelButton, saveButton;
    private InputForm titleInput, quantityInput, descriptionInput;
    private JTextField idInput;
    private MultiSelectFieldPanel<PublisherDTO> publisherInput;
    private MultiSelectFieldPanel<CategoryDTO> categoryInput;
    private MultiSelectFieldPanel<AuthorDTO> authorInput;
    private MultiSelectFieldPanel<ShelfDTO> shelfInput;
    private InputImage coverInput;
    private List<PublisherDTO> listPublisher;
    private List<CategoryDTO> listCategory;
    private List<AuthorDTO> listAuthor;
    private List<ShelfDTO> listShelf;

    public void setListForMultiSelectField (List<PublisherDTO> listPublisher,
                                            List<CategoryDTO> listCategory,
                                            List<AuthorDTO> listAuthor,
                                            List<ShelfDTO> listShelf) {
        this.publisherInput.setItems(listPublisher);
        this.authorInput.setItems(listAuthor);
        this.categoryInput.setItems(listCategory);
        this.shelfInput.setItems(listShelf);




    }

    public void setDefaultTextInField (BookDTO bookDTO) {
        idInput.setText(Integer.toString(bookDTO.getId()));
        titleInput.setText(bookDTO.getTitle());
        authorInput.setSelectedItems(bookDTO.getAuthors());
        categoryInput.setSelectedItems(bookDTO.getCategories());
        quantityInput.setText(Integer.toString(bookDTO.getQuantity()));
        descriptionInput.setText(bookDTO.getDescription());
        shelfInput.setSelectedItems(List.of(bookDTO.getShelf()));
        publisherInput.setSelectedItems(List.of(bookDTO.getPublisher()));

        // todo: check lai cách khởi tạo scale
        coverInput.getBtnChooseImg().setIcon(bookDTO.getCover() == null ? null
                                            : new ImageIcon(coverInput.scale(new ImageIcon(bookDTO.getCover()))));
    }


    public BookDTO getObjectInField () {
        String id = idInput.getText();
        String title = titleInput.getText();
        Set<AuthorDTO> authorSelected = authorInput.getSelectedItems();
        Set<CategoryDTO> categorySelected = categoryInput.getSelectedItems();
        String quantity = quantityInput.getText();
        ShelfDTO shelf = shelfInput.getSelectedItems().iterator().hasNext() ? shelfInput.getSelectedItems().iterator().next() : null;
        PublisherDTO publisherSelected = publisherInput.getSelectedItems().iterator().hasNext() ? publisherInput.getSelectedItems().iterator().next() : null;
        String description = descriptionInput.getText();
        byte[] cover = InputImage.readImage(coverInput.getImgPath());

        return BookDTO.builder()
                        .id(id.isBlank() ? null : Integer.parseInt(id))
                        .title(title)
                        .authors(authorSelected.stream().toList())
                        .publisher(publisherSelected)
                        .categories(categorySelected.stream().toList())
                        .quantity(quantity.matches("^[1-9]\\d*$") ? Integer.parseInt(quantity) : null)
                        .description(description)
                        .shelf(shelf)
                        .cover(cover)
                        .build();
    }


    public BookDialog(JFrame owner, String titleInput, boolean modal, String type) {
        super(owner, titleInput, modal);
        initComponents(titleInput, type);

    }

    public void initCardOne(String type) {


        centerPanel = new JPanel(new BorderLayout());
        bookFormPanel = new JPanel(new GridLayout(3, 4, 0, 0));
        coverPanel = new JPanel();
        titleInput = new InputForm("Tiêu đề");

        idInput = new JTextField("id");
        idInput.setText("");


        authorInput = new MultiSelectFieldPanel<>("Tác giả", listAuthor, o -> o.getFullName());



        categoryInput = new MultiSelectFieldPanel<>("Danh mục", listCategory, o -> String.format("%s", o.getName()));
        quantityInput = new InputForm("Số lượng");
        descriptionInput = new InputForm("Mô tả");
        coverInput = new InputImage("Hình ảnh");
        buttonPanel = new JPanel(new FlowLayout());
        cancelButton = new ButtonCustom("Huỷ bỏ", "danger", 14);
        shelfInput = new MultiSelectFieldPanel<>("Vị trí", listShelf, o -> String.format("%s %s", o.getLocation(), o.getShelfName()));
        publisherInput = new MultiSelectFieldPanel<>("Nhà xuất bản", listPublisher, o -> String.format("%s ", o.getName()));


        bookFormPanel.setBackground(Color.WHITE);

        coverPanel.setBackground(Color.WHITE);
        coverPanel.setPreferredSize(new Dimension(300, 600));
        coverPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        buttonPanel.setBackground(Color.white);

        shelfInput.getField().setSingle(true);
        publisherInput.getField().setSingle(true);

        centerPanel.add(bookFormPanel, BorderLayout.CENTER);
        centerPanel.add(coverPanel, BorderLayout.WEST);
        bookFormPanel.add(titleInput);
        bookFormPanel.add(authorInput);
        bookFormPanel.add(publisherInput);
        bookFormPanel.add(categoryInput);
        bookFormPanel.add(quantityInput);
        bookFormPanel.add(shelfInput);
        bookFormPanel.add(descriptionInput);
        coverPanel.add(coverInput);
        buttonPanel.add(cancelButton);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        switch (type) {
            case "view" -> {
                titleInput.removeAllListeners();
                quantityInput.removeAllListeners();
                descriptionInput.removeAllListeners();
                authorInput.getField().removeTextFieldMouseListeners();
                publisherInput.getField().removeTextFieldMouseListeners();
                categoryInput.getField().removeTextFieldMouseListeners();
                shelfInput.getField().removeTextFieldMouseListeners();
                coverInput.removeAllActions();



            }
            case "update" -> {
                saveButton = new ButtonCustom("Lưu thông tin", "success", 14);
                ButtonCustom btnEditCT = new ButtonCustom("Sửa cấu hình", "warning", 14);
                buttonPanel.add(saveButton);

            }
            case "create" -> {
                createButton = new ButtonCustom("Create", "success", 14);
                buttonPanel.add(createButton);
            }

        }
        quantityInput.getTxtForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // chặn ký tự
                }
            }
        });

        cancelButton.addActionListener(l -> this.dispose());


    }



    public void initComponents(String title, String type) {
        this.setSize(new Dimension(1150, 480));
        this.setLayout(new BorderLayout(0, 0));
        titlePanel = new HeaderTitle(title.toUpperCase());
        mainPanel = new JPanel(new CardLayout());
        initCardOne(type);
        mainPanel.add(centerPanel);
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    public HeaderTitle getTitlePanel() {
        return titlePanel;
    }

    public JPanel getBookFormPanel() {
        return bookFormPanel;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JPanel getCoverPanel() {
        return coverPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public ButtonCustom getCreateButton() {
        return createButton;
    }

    public ButtonCustom getCancelButton() {
        return cancelButton;
    }

    public ButtonCustom getSaveButton() {
        return saveButton;
    }

    public InputForm getTitleInput() {
        return titleInput;
    }

    public InputForm getQuantityInput() {
        return quantityInput;
    }

    public InputForm getDescriptionInput() {
        return descriptionInput;
    }

    public JTextField getIdInput() {
        return idInput;
    }

    public MultiSelectFieldPanel<PublisherDTO> getPublisherInput() {
        return publisherInput;
    }

    public MultiSelectFieldPanel<CategoryDTO> getCategoryInput() {
        return categoryInput;
    }

    public MultiSelectFieldPanel<AuthorDTO> getAuthorInput() {
        return authorInput;
    }

    public MultiSelectFieldPanel<ShelfDTO> getShelfInput() {
        return shelfInput;
    }

    public InputImage getCoverInput() {
        return coverInput;
    }

    public List<PublisherDTO> getListPublisher() {
        return listPublisher;
    }

    public List<CategoryDTO> getListCategory() {
        return listCategory;
    }

    public List<AuthorDTO> getListAuthor() {
        return listAuthor;
    }

    public List<ShelfDTO> getListShelf() {
        return listShelf;
    }

    public void setListPublisher(List<PublisherDTO> list) {
        this.publisherInput.setItems(list);
    }
    public void setListAuthor(List<AuthorDTO> list) {
        this.authorInput.setItems(list);
    }
    public void setListCategory(List<CategoryDTO> list) {
        this.categoryInput.setItems(list);
    }
    public void setListShelf(List<ShelfDTO> list) {
        this.shelfInput.setItems(list);
    }



}


