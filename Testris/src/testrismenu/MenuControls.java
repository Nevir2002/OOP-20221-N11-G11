package testrismenu;

import java.util.*;
import java.io.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class MenuControls extends JFrame implements KeyListener {

    private InputHandler input;
    private boolean duplicate, numericError; // check for key duplication and numeric error to handle
    ArrayList<Integer> listKeyCode;
    Map <Integer, JTextField> NameOfJTextField = new TreeMap<>();

    public MenuControls(InputHandler input) throws Exception {
        initComponents();
        this.setTitle("Cài đặt");
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(28, 28, 28));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.input = input;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("CurrentInput.bi"));
        listKeyCode = (ArrayList<Integer>) ois.readObject();
        ois.close();
//        System.out.println(listKeyCode.size());
//        for(int i = 0; i < listKeyCode.size(); i++){
//            System.out.print(listKeyCode.get(i) + "\t");
//        }
//        System.out.println();
//        for(int i = 0; i < listKeyCode.size()-2; i++){
//            System.out.print(KeyEvent.getKeyText(listKeyCode.get(i)) + "\t");
//        }
//        System.out.println();
        
        //init default
        readKeyCode(moveLeft_Field, 0, listKeyCode.get(0));
        readKeyCode(moveRight_Field, 1, listKeyCode.get(1));
        readKeyCode(softDrop_Field, 2, listKeyCode.get(2));
        readKeyCode(hardDrop_Field, 3, listKeyCode.get(3));
        readKeyCode(rotateL_Field, 4, listKeyCode.get(4));
        readKeyCode(rotateR_Field, 5, listKeyCode.get(5));
        readKeyCode(rotate180_Field, 6, listKeyCode.get(6));
        readKeyCode(hold_Field, 7, listKeyCode.get(7));
        readKeyCode(reset_Field, 8, listKeyCode.get(8));
        readKeyCode(DAS_Field, 9, listKeyCode.get(9));
        readKeyCode(ARR_Field, 10, listKeyCode.get(10));
        
        //khi có key trùng, tắt app đi bật lại thì vào cài đặt, 2 key trùng nó không tô đỏ,
        //phải checkErrorKey mỗi khi mở app lên
//        checkErrorKey();
    }

    public void checkErrorKey() {
        //reset
        duplicate = false;
        for(JTextField jtf : NameOfJTextField.values()) {
            jtf.setBackground(Color.WHITE);
            jtf.setSelectionColor(Color.WHITE);
            jtf.setBorder( new EtchedBorder(Color.WHITE, Color.WHITE));
        }
        
        //những thằng nào xuất hiện > 1 lần thì error
        Map <Integer, Integer> mp = new TreeMap<>();
        for( int i = 0; i < 9; i++ ) {
            if( !mp.containsKey(listKeyCode.get(i))) mp.put(listKeyCode.get(i), 1);
            else mp.put(listKeyCode.get(i), mp.get(listKeyCode.get(i)) + 1);
        }
        for( int i = 0; i < 9; i++ ) {
            if( mp.get(listKeyCode.get(i)) > 1 ) {
                duplicate = true;
                NameOfJTextField.get(i).setBorder( new EtchedBorder(Color.RED, Color.RED));
                NameOfJTextField.get(i).setSelectionColor(Color.RED);
                NameOfJTextField.get(i).setBackground(Color.RED);
            }
        }
    }

    public void readKeyCode(JTextField keyMove, int idx, int keyCode) {
        //init default keycode when open game
        if(idx < 9){
            
            keyMove.setText(KeyEvent.getKeyText(keyCode).toLowerCase());
            NameOfJTextField.put(idx, keyMove);
            
        } else keyMove.setText(String.valueOf(keyCode));

        KeyListener keyListener = new KeyListener() {
            //keyPressed chạy khi ấn nút, nó chạy trước khi hiện ra giá trị
            //ví dụ ấn a, thì nó chạy xong mới hiện value là chữ a
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(idx < 9) keyMove.selectAll();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                //phải dùng thằng này nếu muốn nhận thêm mấy key kiểu F1, F2,...
                int kc = keyEvent.getExtendedKeyCode();
                String keyText = KeyEvent.getKeyText(kc).toLowerCase();
//                else if( kc == 8) keyMove.setText("backspace");
//                else keyMove.setText(keyText);
                if(idx < 9){
                    if( kc == 37 ) keyMove.setText("left");
                    if( kc == 39 ) keyMove.setText("right");
                    if( kc == 38 ) keyMove.setText("up");
                    if( kc == 40 ) keyMove.setText("down");
                    if( kc == 32 ) keyMove.setText("space");
                    listKeyCode.set(idx, kc);
                } else{
                    try{
                        keyMove.setBorder( new EtchedBorder(Color.WHITE, Color.WHITE));
                        keyMove.setSelectionColor(Color.WHITE);
                        keyMove.setBackground(Color.WHITE);
                        String str = keyMove.getText().trim();
                        listKeyCode.set(idx, Integer.parseInt(str));
                        System.out.println(idx + " " + str);
                        keyMove.setText(str); // trim inside the text field
                        numericError = false;
                    }catch(NumberFormatException e){
                        keyMove.setBorder( new EtchedBorder(Color.RED, Color.RED));
                        keyMove.setSelectionColor(Color.RED);
                        keyMove.setBackground(Color.RED);
                        numericError = true;
                    }
                }
                
                checkErrorKey();
            }
            @Override
            public void keyTyped(KeyEvent keyEvent) {}
            
        };

        keyMove.addKeyListener(keyListener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jProgressBar1 = new javax.swing.JProgressBar();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        menuAreaPlaceholder = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        moveLeft_Field = new javax.swing.JTextField();
        moveRight_Field = new javax.swing.JTextField();
        softDrop_Field = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        hardDrop_Field = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        rotateL_Field = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        rotateR_Field = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        rotate180_Field = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        DAS_Field = new javax.swing.JTextField();
        ARR_Field = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        hold_Field = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        reset_Field = new javax.swing.JTextField();
        saveAndClose = new javax.swing.JButton();
        resetDefault = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuAreaPlaceholder.setBackground(new java.awt.Color(28, 28, 28));
        menuAreaPlaceholder.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Phím điều khiển game");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setAlignmentY(0.0F);

        jLabel2.setFont(new java.awt.Font("Arial Unicode MS", 2, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nhấn vào ô bất kì và thay bằng phím bạn muốn.");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Di chuyển trái:");

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Di chuyển phải:");

        moveLeft_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        moveLeft_Field.setText("left");
        moveLeft_Field.setAutoscrolls(false);
        moveLeft_Field.setBorder(null);
        moveLeft_Field.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        moveLeft_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        moveLeft_Field.setOpaque(true);
        moveLeft_Field.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        moveLeft_Field.setSelectionColor(new java.awt.Color(255, 255, 255));
        moveLeft_Field.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                moveLeft_FieldMouseClicked(evt);
            }
        });
        moveLeft_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveLeft_FieldActionPerformed(evt);
            }
        });
        moveLeft_Field.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                moveLeft_FieldPropertyChange(evt);
            }
        });
        moveLeft_Field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                moveLeft_FieldKeyPressed(evt);
            }
        });

        moveRight_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        moveRight_Field.setText("right");
        moveRight_Field.setAutoscrolls(false);
        moveRight_Field.setBorder(null);
        moveRight_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        moveRight_Field.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        moveRight_Field.setSelectionColor(new java.awt.Color(255, 255, 255));
        moveRight_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveRight_FieldActionPerformed(evt);
            }
        });

        softDrop_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        softDrop_Field.setText("down");
        softDrop_Field.setAutoscrolls(false);
        softDrop_Field.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        softDrop_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        softDrop_Field.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        softDrop_Field.setSelectionColor(new java.awt.Color(255, 255, 255));
        softDrop_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                softDrop_FieldActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Thả nhẹ:");

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Thả mạnh:");

        hardDrop_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        hardDrop_Field.setText("space");
        hardDrop_Field.setAutoscrolls(false);
        hardDrop_Field.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        hardDrop_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        hardDrop_Field.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        hardDrop_Field.setSelectionColor(new java.awt.Color(255, 255, 255));
        hardDrop_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hardDrop_FieldActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Xoay trái:");

        rotateL_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        rotateL_Field.setText("z");
        rotateL_Field.setAutoscrolls(false);
        rotateL_Field.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        rotateL_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rotateL_Field.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        rotateL_Field.setSelectionColor(new java.awt.Color(255, 255, 255));
        rotateL_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateL_FieldActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Xoay phải:");

        rotateR_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        rotateR_Field.setText("up");
        rotateR_Field.setAutoscrolls(false);
        rotateR_Field.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        rotateR_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rotateR_Field.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        rotateR_Field.setSelectionColor(new java.awt.Color(255, 255, 255));
        rotateR_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateR_FieldActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Xoay 180:");

        rotate180_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        rotate180_Field.setText("a");
        rotate180_Field.setAutoscrolls(false);
        rotate180_Field.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        rotate180_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rotate180_Field.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        rotate180_Field.setSelectionColor(new java.awt.Color(255, 255, 255));
        rotate180_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotate180_FieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cài đặt game");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel4.setAlignmentY(0.0F);

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("DAS:");

        DAS_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        DAS_Field.setText("133");
        DAS_Field.setAutoscrolls(false);
        DAS_Field.setBorder(null);
        DAS_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        DAS_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DAS_FieldActionPerformed(evt);
            }
        });

        ARR_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        ARR_Field.setText("10");
        ARR_Field.setAutoscrolls(false);
        ARR_Field.setBorder(null);
        ARR_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ARR_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ARR_FieldActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ARR:");

        jLabel17.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Giữ:");

        hold_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        hold_Field.setText("c");
        hold_Field.setAutoscrolls(false);
        hold_Field.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        hold_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        hold_Field.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        hold_Field.setSelectionColor(new java.awt.Color(255, 255, 255));
        hold_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hold_FieldActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Reset:");

        reset_Field.setFont(new java.awt.Font("Source Code Pro", 1, 16)); // NOI18N
        reset_Field.setText("r");
        reset_Field.setAutoscrolls(false);
        reset_Field.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        reset_Field.setMargin(new java.awt.Insets(0, 0, 0, 0));
        reset_Field.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        reset_Field.setSelectionColor(new java.awt.Color(255, 255, 255));
        reset_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_FieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuAreaPlaceholderLayout = new javax.swing.GroupLayout(menuAreaPlaceholder);
        menuAreaPlaceholder.setLayout(menuAreaPlaceholderLayout);
        menuAreaPlaceholderLayout.setHorizontalGroup(
            menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuAreaPlaceholderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuAreaPlaceholderLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(reset_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(menuAreaPlaceholderLayout.createSequentialGroup()
                        .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DAS_Field)
                            .addComponent(ARR_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(menuAreaPlaceholderLayout.createSequentialGroup()
                        .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(moveLeft_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(moveRight_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(softDrop_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hardDrop_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotateL_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotateR_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotate180_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hold_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        menuAreaPlaceholderLayout.setVerticalGroup(
            menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuAreaPlaceholderLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moveLeft_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(3, 3, 3)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(moveRight_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(softDrop_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(hardDrop_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(rotateL_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(rotateR_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(rotate180_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(hold_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(reset_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DAS_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(3, 3, 3)
                .addGroup(menuAreaPlaceholderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(ARR_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        saveAndClose.setBackground(new java.awt.Color(161, 161, 161));
        saveAndClose.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        saveAndClose.setText("Lưu và đóng điều khiển");
        saveAndClose.setToolTipText("");
        saveAndClose.setAlignmentY(0.0F);
        saveAndClose.setBorder(null);
        saveAndClose.setBorderPainted(false);
        saveAndClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveAndClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveAndClose.setMargin(new java.awt.Insets(0, 0, 0, 0));
        saveAndClose.setOpaque(true);
        saveAndClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAndCloseActionPerformed(evt);
            }
        });

        resetDefault.setBackground(new java.awt.Color(161, 161, 161));
        resetDefault.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        resetDefault.setText("Đặt lại mặc định");
        resetDefault.setToolTipText("");
        resetDefault.setAlignmentY(0.0F);
        resetDefault.setBorder(null);
        resetDefault.setBorderPainted(false);
        resetDefault.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetDefault.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resetDefault.setMargin(new java.awt.Insets(0, 0, 0, 0));
        resetDefault.setOpaque(true);
        resetDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetDefaultActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuAreaPlaceholder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveAndClose, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(resetDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(menuAreaPlaceholder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveAndClose, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateFile(String s) throws Exception{
        
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(s));
        oos.writeObject(listKeyCode);
        oos.close();
        
    }
    
    private void saveAndCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAndCloseActionPerformed
        
        if(duplicate || numericError){
            
            JOptionPane.showMessageDialog(null, "Invalid settings");
            
        }else{
            
            input.updateInput(listKeyCode);
            try {
                updateFile("CurrentInput.bi");
            } catch (Exception ex) {
                System.out.println("Update input settings to file has failed.");
                Logger.getLogger(MenuControls.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.setVisible(false);
            
        }
    }//GEN-LAST:event_saveAndCloseActionPerformed

    private void ARR_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ARR_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ARR_FieldActionPerformed

    private void DAS_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DAS_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DAS_FieldActionPerformed

    private void rotate180_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotate180_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rotate180_FieldActionPerformed

    private void rotateR_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateR_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rotateR_FieldActionPerformed

    private void rotateL_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateL_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rotateL_FieldActionPerformed

    private void hardDrop_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardDrop_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hardDrop_FieldActionPerformed

    private void softDrop_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_softDrop_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_softDrop_FieldActionPerformed

    private void moveRight_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveRight_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moveRight_FieldActionPerformed

    private void moveLeft_FieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_moveLeft_FieldKeyPressed

    }//GEN-LAST:event_moveLeft_FieldKeyPressed

    private void moveLeft_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveLeft_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moveLeft_FieldActionPerformed

    private void moveLeft_FieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moveLeft_FieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_moveLeft_FieldMouseClicked

    private void moveLeft_FieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_moveLeft_FieldPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_moveLeft_FieldPropertyChange

    private void hold_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hold_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hold_FieldActionPerformed

    private void reset_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reset_FieldActionPerformed

    private void resetDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetDefaultActionPerformed
            
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("DefaultInput.bi"));
            listKeyCode = (ArrayList<Integer>) ois.readObject();
            ois.close();
            updateFile("CurrentInput.bi");
            input.updateInput(listKeyCode);
            readKeyCode(moveLeft_Field, 0, listKeyCode.get(0));
            readKeyCode(moveRight_Field, 1, listKeyCode.get(1));
            readKeyCode(softDrop_Field, 2, listKeyCode.get(2));
            readKeyCode(hardDrop_Field, 3, listKeyCode.get(3));
            readKeyCode(rotateL_Field, 4, listKeyCode.get(4));
            readKeyCode(rotateR_Field, 5, listKeyCode.get(5));
            readKeyCode(rotate180_Field, 6, listKeyCode.get(6));
            readKeyCode(hold_Field, 7, listKeyCode.get(7));
            readKeyCode(reset_Field, 8, listKeyCode.get(8));
            readKeyCode(DAS_Field, 9, listKeyCode.get(9));
            readKeyCode(ARR_Field, 10, listKeyCode.get(10));
        }catch(Exception e){
            System.out.println("Can't read default file.");
        }
            
    }//GEN-LAST:event_resetDefaultActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuControls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuControls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuControls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuControls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                MenuControls mc = new MenuControls(this.input);
//            mc.setVisible(false);
//                new MenuControls(input).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ARR_Field;
    private javax.swing.JTextField DAS_Field;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JTextField hardDrop_Field;
    private javax.swing.JTextField hold_Field;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JPanel menuAreaPlaceholder;
    private javax.swing.JTextField moveLeft_Field;
    private javax.swing.JTextField moveRight_Field;
    private javax.swing.JButton resetDefault;
    private javax.swing.JTextField reset_Field;
    private javax.swing.JTextField rotate180_Field;
    private javax.swing.JTextField rotateL_Field;
    private javax.swing.JTextField rotateR_Field;
    private javax.swing.JButton saveAndClose;
    private javax.swing.JTextField softDrop_Field;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
