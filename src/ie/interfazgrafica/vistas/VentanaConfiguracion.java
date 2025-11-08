/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ie.interfazgrafica.vistas;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

/**
 *
 * @author Valentina
 */
public class VentanaConfiguracion extends javax.swing.JFrame {

    /**
     * Creates new form VentanaConfiguracion
     */
    public VentanaConfiguracion() {
        initComponents();
    }
    
        // === GETTERS PARA CONTROLADOR ===
      public JButton getBtnAgregar() { return btnAgregar; }
      public JButton getBtnEliminar() { return btnEliminar; }
      public JButton getBtnIniciarBatalla() { return btnIniciarBatalla; }
      public JButton getBtnCargarBatalla() { return btnCargarBatalla; }
      public JButton getBtnSalir() { return btnSalir; }

      // Campos de registro de jugadores
      public JTextField getTxtNombre() { return txtNombre; }
      public JTextField getTxtApodo() { return txtApodo; }
      public JTable getTablaPersonajes() { return tablaPersonajes; }
      public JRadioButton getRbHeroe() { return rbHeroe; }
      public JRadioButton getRbVillano() { return rbVillano; }

      // === CAMPOS DE CONFIGURACIÓN DE BATALLA ===

      // Campos generales
      public JComboBox<String> getCbCantidadBatallas() { return cbCantidadBatallas; }
      public JCheckBox getCkActivar1() { return ckActivar1; }
      public JCheckBox getCkDesactivar() { return ckDesactivar; }

      // === CAMPOS NUEVOS: ESTADÍSTICAS POR PERSONAJE ===

      // --- HÉROE ---
      public JTextField getTxtVidaHeroe() { return txtVidaHeroe; }
      public JTextField getTxtFuerzaHeroe() { return txtFuerzaHeroe; }
      public JTextField getTxtDefensaHeroe() { return txtDefensaHeroe; }
      public JTextField getTxtBendicionHeroe() { return txtBendicionHeroe; }
      public JComboBox<String> getCbApodoHeroe() { return cbApodoHeroe; }

      // --- VILLANO ---
      public JTextField getTxtVidaVillano() { return txtVidaVillano; }
      public JTextField getTxtFuerzaVillano() { return txtFuerzaVillano; }
      public JTextField getTxtDefensaVillano() { return txtDefensaVillano; }
      public JTextField getTxtBendicionVillano() { return txtBendicionVillano; }
      public JComboBox<String> getCbApodoVillano() { return cbApodoVillano; }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        Panel = new javax.swing.JTabbedPane();
        RegistroJugadores = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPersonajes = new javax.swing.JTable();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jPanel1 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApodo = new javax.swing.JLabel();
        txtApodo = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        rbHeroe = new javax.swing.JRadioButton();
        rbVillano = new javax.swing.JRadioButton();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        ConfiguracionPartidas = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblVidaInicial = new javax.swing.JLabel();
        lblFuerzaInicial = new javax.swing.JLabel();
        txtVidaHeroe = new javax.swing.JTextField();
        txtFuerzaHeroe = new javax.swing.JTextField();
        lblDefensaInicial = new javax.swing.JLabel();
        txtDefensaHeroe = new javax.swing.JTextField();
        lblBendicionInicial = new javax.swing.JLabel();
        txtBendicionHeroe = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lblVidaInicial1 = new javax.swing.JLabel();
        cbApodoHeroe = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        lblVidaInicial2 = new javax.swing.JLabel();
        lblFuerzaInicial1 = new javax.swing.JLabel();
        txtVidaVillano = new javax.swing.JTextField();
        txtFuerzaVillano = new javax.swing.JTextField();
        lblDefensaInicial1 = new javax.swing.JLabel();
        txtDefensaVillano = new javax.swing.JTextField();
        lblBendicionInicial1 = new javax.swing.JLabel();
        txtBendicionVillano = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblVidaInicial3 = new javax.swing.JLabel();
        cbApodoVillano = new javax.swing.JComboBox<>();
        btnIniciarBatalla = new javax.swing.JButton();
        btnCargarBatalla = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblCantidadBatallas1 = new javax.swing.JLabel();
        cbCantidadBatallas = new javax.swing.JComboBox<>();
        lblAtaques = new javax.swing.JLabel();
        ckActivar1 = new javax.swing.JCheckBox();
        ckDesactivar = new javax.swing.JCheckBox();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        RegistroJugadores.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 0));
        jLabel2.setText("TABLA DE PERSONAJES");

        tablaPersonajes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre", "Apodo", "Tipo de pesonaje"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaPersonajes);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));

        lblNombre.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblNombre.setText("Nombre:");

        lblApodo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblApodo.setText("Apodo:");

        lblTipo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblTipo.setText("Tipo de personaje:");

        rbHeroe.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        rbHeroe.setText("Heroe");

        rbVillano.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        rbVillano.setText("Villano");

        btnAgregar.setBackground(new java.awt.Color(0, 102, 0));
        btnAgregar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(0, 102, 0));
        btnEliminar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTipo)
                        .addGap(37, 37, 37)
                        .addComponent(rbHeroe)
                        .addGap(44, 44, 44)
                        .addComponent(rbVillano))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnAgregar)
                            .addGap(27, 27, 27)
                            .addComponent(btnEliminar)
                            .addGap(40, 40, 40))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNombre)
                                .addComponent(lblApodo))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombre)
                                .addComponent(txtApodo, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApodo)
                    .addComponent(txtApodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(rbHeroe)
                    .addComponent(rbVillano))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnEliminar))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 0));
        jLabel1.setText("REGISTRO DE PERSONAJES");

        javax.swing.GroupLayout RegistroJugadoresLayout = new javax.swing.GroupLayout(RegistroJugadores);
        RegistroJugadores.setLayout(RegistroJugadoresLayout);
        RegistroJugadoresLayout.setHorizontalGroup(
            RegistroJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegistroJugadoresLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(RegistroJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegistroJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RegistroJugadoresLayout.createSequentialGroup()
                        .addGroup(RegistroJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RegistroJugadoresLayout.createSequentialGroup()
                                .addGap(202, 202, 202)
                                .addComponent(jLabel2))
                            .addGroup(RegistroJugadoresLayout.createSequentialGroup()
                                .addGap(184, 184, 184)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(334, 334, 334))
        );
        RegistroJugadoresLayout.setVerticalGroup(
            RegistroJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegistroJugadoresLayout.createSequentialGroup()
                .addGroup(RegistroJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RegistroJugadoresLayout.createSequentialGroup()
                        .addGap(281, 281, 281)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RegistroJugadoresLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        Panel.addTab("Registro de jugadores", RegistroJugadores);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 0));
        jLabel3.setText("CONFIGURACION DE PARTIDAS");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51)));

        lblVidaInicial.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblVidaInicial.setText("Vida inicial:");

        lblFuerzaInicial.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblFuerzaInicial.setText("Fuerza inicial:");

        lblDefensaInicial.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblDefensaInicial.setText("Defensa inicial:");

        lblBendicionInicial.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblBendicionInicial.setText("Bendicion inicial:");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel4.setText("JUGADOR HEROE");

        lblVidaInicial1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblVidaInicial1.setText("Apodo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBendicionInicial)
                    .addComponent(lblDefensaInicial)
                    .addComponent(lblFuerzaInicial)
                    .addComponent(lblVidaInicial)
                    .addComponent(lblVidaInicial1))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtBendicionHeroe, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDefensaHeroe, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFuerzaHeroe, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtVidaHeroe, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbApodoHeroe, javax.swing.GroupLayout.Alignment.LEADING, 0, 377, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(242, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(214, 214, 214))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaInicial1)
                    .addComponent(cbApodoHeroe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVidaHeroe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVidaInicial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFuerzaHeroe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFuerzaInicial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefensaInicial)
                    .addComponent(txtDefensaHeroe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBendicionInicial)
                    .addComponent(txtBendicionHeroe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51)));

        lblVidaInicial2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblVidaInicial2.setText("Vida inicial:");

        lblFuerzaInicial1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblFuerzaInicial1.setText("Fuerza inicial:");

        lblDefensaInicial1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblDefensaInicial1.setText("Defensa inicial:");

        lblBendicionInicial1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblBendicionInicial1.setText("Bendicion inicial:");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel5.setText("JUGADOR VILLANO");

        lblVidaInicial3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblVidaInicial3.setText("Apodo:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBendicionInicial1)
                    .addComponent(lblDefensaInicial1)
                    .addComponent(lblFuerzaInicial1)
                    .addComponent(lblVidaInicial2)
                    .addComponent(lblVidaInicial3))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtBendicionVillano, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDefensaVillano, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFuerzaVillano, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtVidaVillano, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbApodoVillano, javax.swing.GroupLayout.Alignment.LEADING, 0, 377, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(228, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(214, 214, 214))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaInicial3)
                    .addComponent(cbApodoVillano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVidaVillano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVidaInicial2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFuerzaVillano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFuerzaInicial1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDefensaInicial1)
                    .addComponent(txtDefensaVillano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBendicionInicial1)
                    .addComponent(txtBendicionVillano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btnIniciarBatalla.setBackground(new java.awt.Color(0, 102, 0));
        btnIniciarBatalla.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        btnIniciarBatalla.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciarBatalla.setText("Iniciar Batalla");

        btnCargarBatalla.setBackground(new java.awt.Color(0, 102, 0));
        btnCargarBatalla.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        btnCargarBatalla.setForeground(new java.awt.Color(255, 255, 255));
        btnCargarBatalla.setText("Cargar Batalla");

        btnSalir.setBackground(new java.awt.Color(204, 204, 204));
        btnSalir.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblCantidadBatallas1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblCantidadBatallas1.setText("Cantidad de batallas:");

        cbCantidadBatallas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "5", " " }));

        lblAtaques.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        lblAtaques.setText("Ataques supremos:");

        ckActivar1.setText("Activar");

        ckDesactivar.setText("Desactivar");

        javax.swing.GroupLayout ConfiguracionPartidasLayout = new javax.swing.GroupLayout(ConfiguracionPartidas);
        ConfiguracionPartidas.setLayout(ConfiguracionPartidasLayout);
        ConfiguracionPartidasLayout.setHorizontalGroup(
            ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfiguracionPartidasLayout.createSequentialGroup()
                .addGroup(ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConfiguracionPartidasLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ConfiguracionPartidasLayout.createSequentialGroup()
                        .addGroup(ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ConfiguracionPartidasLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(lblCantidadBatallas1))
                            .addGroup(ConfiguracionPartidasLayout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(lblAtaques)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ConfiguracionPartidasLayout.createSequentialGroup()
                                .addComponent(ckActivar1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ConfiguracionPartidasLayout.createSequentialGroup()
                                        .addComponent(btnIniciarBatalla)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCargarBatalla)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(ckDesactivar)))
                            .addComponent(cbCantidadBatallas, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ConfiguracionPartidasLayout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel3)))
                .addContainerGap(327, Short.MAX_VALUE))
        );
        ConfiguracionPartidasLayout.setVerticalGroup(
            ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfiguracionPartidasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCantidadBatallas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCantidadBatallas1))
                .addGap(7, 7, 7)
                .addGroup(ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckActivar1)
                    .addComponent(ckDesactivar)
                    .addComponent(lblAtaques))
                .addGap(18, 18, 18)
                .addGroup(ConfiguracionPartidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciarBatalla)
                    .addComponent(btnCargarBatalla)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel.addTab("Configuracion de partidas", ConfiguracionPartidas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaConfiguracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaConfiguracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaConfiguracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaConfiguracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaConfiguracion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ConfiguracionPartidas;
    private javax.swing.JTabbedPane Panel;
    private javax.swing.JPanel RegistroJugadores;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCargarBatalla;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnIniciarBatalla;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbApodoHeroe;
    private javax.swing.JComboBox<String> cbApodoVillano;
    private javax.swing.JComboBox<String> cbCantidadBatallas;
    private javax.swing.JCheckBox ckActivar1;
    private javax.swing.JCheckBox ckDesactivar;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApodo;
    private javax.swing.JLabel lblAtaques;
    private javax.swing.JLabel lblBendicionInicial;
    private javax.swing.JLabel lblBendicionInicial1;
    private javax.swing.JLabel lblCantidadBatallas1;
    private javax.swing.JLabel lblDefensaInicial;
    private javax.swing.JLabel lblDefensaInicial1;
    private javax.swing.JLabel lblFuerzaInicial;
    private javax.swing.JLabel lblFuerzaInicial1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblVidaInicial;
    private javax.swing.JLabel lblVidaInicial1;
    private javax.swing.JLabel lblVidaInicial2;
    private javax.swing.JLabel lblVidaInicial3;
    private javax.swing.JRadioButton rbHeroe;
    private javax.swing.JRadioButton rbVillano;
    private javax.swing.JTable tablaPersonajes;
    private javax.swing.JTextField txtApodo;
    private javax.swing.JTextField txtBendicionHeroe;
    private javax.swing.JTextField txtBendicionVillano;
    private javax.swing.JTextField txtDefensaHeroe;
    private javax.swing.JTextField txtDefensaVillano;
    private javax.swing.JTextField txtFuerzaHeroe;
    private javax.swing.JTextField txtFuerzaVillano;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtVidaHeroe;
    private javax.swing.JTextField txtVidaVillano;
    // End of variables declaration//GEN-END:variables
}
