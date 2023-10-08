package com.yazlab.myapplication;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Alphabet{
    private ArrayList<LinkedHashMap<String,Object>> alphabet_unordered;
    //private ArrayList<LinkedHashMap<String,Object>> alphabet_consonants;
    private LinkedHashMap<String,Object> h;

    public Alphabet(){
        alphabet_unordered =new ArrayList<LinkedHashMap<String,Object>>();
        //alphabet_consonants =new ArrayList<LinkedHashMap<String,Object>>();

        h=new LinkedHashMap<>();
        h.put("character","A");
        h.put("image_selected",R.drawable.a_s);
        h.put("image_unselected",R.drawable.a);
        h.put("image_ice",R.drawable.a_ice);
        h.put("image_ice2",R.drawable.a_ice2);
        h.put("image_x",R.drawable.a_x);
        h.put("point_of_character",1);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","E");
        h.put("image_selected",R.drawable.e_s);
        h.put("image_unselected",R.drawable.e);
        h.put("image_ice",R.drawable.e_ice);
        h.put("image_ice2",R.drawable.e_ice2);
        h.put("image_x",R.drawable.e_x);
        h.put("point_of_character",1);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","I");
        h.put("image_selected",R.drawable.i1_s);
        h.put("image_unselected",R.drawable.i1);
        h.put("image_ice",R.drawable.i1_ice);
        h.put("image_ice2",R.drawable.i1_ice2);
        h.put("image_x",R.drawable.i1_x);
        h.put("point_of_character",2);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","İ");
        h.put("image_selected",R.drawable.i2_s);
        h.put("image_unselected",R.drawable.i2);
        h.put("image_ice",R.drawable.i2_ice);
        h.put("image_ice2",R.drawable.i2_ice2);
        h.put("image_x",R.drawable.i2_x);
        h.put("point_of_character",1);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","O");
        h.put("image_selected",R.drawable.o1_s);
        h.put("image_unselected",R.drawable.o1);
        h.put("image_ice",R.drawable.o1_ice);
        h.put("image_ice2",R.drawable.o1_ice2);
        h.put("image_x",R.drawable.o1_x);
        h.put("point_of_character",2);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","Ö");
        h.put("image_selected",R.drawable.o2_s);
        h.put("image_unselected",R.drawable.o2);
        h.put("image_ice",R.drawable.o2_ice);
        h.put("image_ice2",R.drawable.o2_ice2);
        h.put("image_x",R.drawable.o2_x);
        h.put("point_of_character",7);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","U");
        h.put("image_selected",R.drawable.u1_s);
        h.put("image_unselected",R.drawable.u1);
        h.put("image_ice",R.drawable.u1_ice);
        h.put("image_ice2",R.drawable.u1_ice2);
        h.put("image_x",R.drawable.u1_x);
        h.put("point_of_character",2);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","Ü");
        h.put("image_selected",R.drawable.u2_s);
        h.put("image_unselected",R.drawable.u2);
        h.put("image_ice",R.drawable.u2_ice);
        h.put("image_ice2",R.drawable.u2_ice2);
        h.put("image_x",R.drawable.u2_x);
        h.put("point_of_character",3);
        alphabet_unordered.add(h);


        h=new LinkedHashMap<>();
        h.put("character","B");
        h.put("image_selected",R.drawable.b_s);
        h.put("image_unselected",R.drawable.b);
        h.put("image_ice",R.drawable.b_ice);
        h.put("image_ice2",R.drawable.b_ice2);
        h.put("image_x",R.drawable.b_x);
        h.put("point_of_character",3);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","C");
        h.put("image_selected",R.drawable.c1_s);
        h.put("image_unselected",R.drawable.c1);
        h.put("image_ice",R.drawable.c1_ice);
        h.put("image_ice2",R.drawable.c1_ice2);
        h.put("image_x",R.drawable.c1_x);
        h.put("point_of_character",4);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","Ç");
        h.put("image_selected",R.drawable.c2_s);
        h.put("image_unselected",R.drawable.c2);
        h.put("image_ice",R.drawable.c2_ice);
        h.put("image_ice2",R.drawable.c2_ice2);
        h.put("image_x",R.drawable.c2_x);
        h.put("point_of_character",4);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","D");
        h.put("image_selected",R.drawable.d_s);
        h.put("image_unselected",R.drawable.d);
        h.put("image_ice",R.drawable.d_ice);
        h.put("image_ice2",R.drawable.d_ice2);
        h.put("image_x",R.drawable.d_x);
        h.put("point_of_character",3);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","F");
        h.put("image_selected",R.drawable.f_s);
        h.put("image_unselected",R.drawable.f);
        h.put("image_ice",R.drawable.f_ice);
        h.put("image_ice2",R.drawable.f_ice2);
        h.put("image_x",R.drawable.f_x);
        h.put("point_of_character",7);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","G");
        h.put("image_selected",R.drawable.g1_s);
        h.put("image_unselected",R.drawable.g1);
        h.put("image_ice",R.drawable.g1_ice);
        h.put("image_ice2",R.drawable.g1_ice2);
        h.put("image_x",R.drawable.g1_x);
        h.put("point_of_character",5);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","Ğ");
        h.put("image_selected",R.drawable.g2_s);
        h.put("image_unselected",R.drawable.g2);
        h.put("image_ice",R.drawable.g2_ice);
        h.put("image_ice2",R.drawable.g2_ice2);
        h.put("image_x",R.drawable.g2_x);
        h.put("point_of_character",8);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","H");
        h.put("image_selected",R.drawable.h_s);
        h.put("image_unselected",R.drawable.h);
        h.put("image_ice",R.drawable.h_ice);
        h.put("image_ice2",R.drawable.h_ice2);
        h.put("image_x",R.drawable.h_x);
        h.put("point_of_character",5);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","J");
        h.put("image_selected",R.drawable.j_s);
        h.put("image_unselected",R.drawable.j);
        h.put("image_ice",R.drawable.j_ice);
        h.put("image_ice2",R.drawable.j_ice2);
        h.put("image_x",R.drawable.j_x);
        h.put("point_of_character",10);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","K");
        h.put("image_selected",R.drawable.k_s);
        h.put("image_unselected",R.drawable.k);
        h.put("image_ice",R.drawable.k_ice);
        h.put("image_ice2",R.drawable.k_ice2);
        h.put("image_x",R.drawable.k_x);
        h.put("point_of_character",1);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","L");
        h.put("image_selected",R.drawable.l_s);
        h.put("image_unselected",R.drawable.l);
        h.put("image_ice",R.drawable.l_ice);
        h.put("image_ice2",R.drawable.l_ice2);
        h.put("image_x",R.drawable.l_x);
        h.put("point_of_character",1);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","M");
        h.put("image_selected",R.drawable.m_s);
        h.put("image_unselected",R.drawable.m);
        h.put("image_ice",R.drawable.m_ice);
        h.put("image_ice2",R.drawable.m_ice2);
        h.put("image_x",R.drawable.m_x);
        h.put("point_of_character",2);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","N");
        h.put("image_selected",R.drawable.n_s);
        h.put("image_unselected",R.drawable.n);
        h.put("image_ice",R.drawable.n_ice);
        h.put("image_ice2",R.drawable.n_ice2);
        h.put("image_x",R.drawable.n_x);
        h.put("point_of_character",1);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","P");
        h.put("image_selected",R.drawable.p_s);
        h.put("image_unselected",R.drawable.p);
        h.put("image_ice",R.drawable.p_ice);
        h.put("image_ice2",R.drawable.p_ice2);
        h.put("image_x",R.drawable.p_x);
        h.put("point_of_character",5);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","R");
        h.put("image_selected",R.drawable.r_s);
        h.put("image_unselected",R.drawable.r);
        h.put("image_ice",R.drawable.r_ice);
        h.put("image_ice2",R.drawable.r_ice2);
        h.put("image_x",R.drawable.r_x);
        h.put("point_of_character",1);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","S");
        h.put("image_selected",R.drawable.s1_s);
        h.put("image_unselected",R.drawable.s1);
        h.put("image_ice",R.drawable.s1_ice);
        h.put("image_ice2",R.drawable.s1_ice2);
        h.put("image_x",R.drawable.s1_x);
        h.put("point_of_character",2);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","Ş");
        h.put("image_selected",R.drawable.s2_s);
        h.put("image_unselected",R.drawable.s2);
        h.put("image_ice",R.drawable.s2_ice);
        h.put("image_ice2",R.drawable.s2_ice2);
        h.put("image_x",R.drawable.s2_x);
        h.put("point_of_character",4);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","T");
        h.put("image_selected",R.drawable.t_s);
        h.put("image_unselected",R.drawable.t);
        h.put("image_ice",R.drawable.t_ice);
        h.put("image_ice2",R.drawable.t_ice2);
        h.put("image_x",R.drawable.t_x);
        h.put("point_of_character",1);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","V");
        h.put("image_selected",R.drawable.v_s);
        h.put("image_unselected",R.drawable.v);
        h.put("image_ice",R.drawable.v_ice);
        h.put("image_ice2",R.drawable.v_ice2);
        h.put("image_x",R.drawable.v_x);
        h.put("point_of_character",7);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","Y");
        h.put("image_selected",R.drawable.y_s);
        h.put("image_unselected",R.drawable.y);
        h.put("image_ice",R.drawable.y_ice);
        h.put("image_ice2",R.drawable.y_ice2);
        h.put("image_x",R.drawable.y_x);
        h.put("point_of_character",3);
        alphabet_unordered.add(h);

        h=new LinkedHashMap<>();
        h.put("character","Z");
        h.put("image_selected",R.drawable.z_s);
        h.put("image_unselected",R.drawable.z);
        h.put("image_ice",R.drawable.z_ice);
        h.put("image_ice2",R.drawable.z_ice2);
        h.put("image_x",R.drawable.z_x);
        h.put("point_of_character",4);
        alphabet_unordered.add(h);

    }

    public ArrayList<LinkedHashMap<String,Object>> getAlphabet_unordered(){
        return alphabet_unordered;
    }


}
