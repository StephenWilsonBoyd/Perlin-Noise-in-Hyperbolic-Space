package src;
import java.awt.*;
import javax.swing.JFrame;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.JFrame;
import java.awt.image.*;
import javax.imageio.*;
import java.util.*;
import java.io.*;

public class HighResHyperbolaWorld{
    
    
    public static void main(String[] args){
        BufferedImage output = new BufferedImage(10001,10001,BufferedImage.TYPE_INT_RGB);
        double[][] world = new double[256][];
        double[][] world128 = new double[128][];
        double[][] world64 = new double[64][];
        double[][] world32 = new double[32][];
        double[][] world16 = new double[16][];
        double[][] world8 = new double[8][];
        double[][] world4 = new double[4][];
        double[] ring;
        for(double r=0;r<8;r+=2){
            ring = new double[Math.max(1,(int)(0.5*Math.ceil(4*Math.PI*Math.sinh(r/2.0)*Math.sinh(r/2.0)-4*Math.PI*Math.sinh((r-1)/2.0)*Math.sinh((r-1)/2.0))))];
            for(int x=0;x<ring.length;x++)ring[x]=Math.random()*2-1;
            world4[(int)(r/2)]=ring;
        }
        for(double r=0;r<8;r+=1){
            ring = new double[Math.max(1,(int)(Math.ceil(4*Math.PI*Math.sinh(r/2.0)*Math.sinh(r/2.0)-4*Math.PI*Math.sinh((r-1)/2.0)*Math.sinh((r-1)/2.0))))];
            for(int x=0;x<ring.length;x++)ring[x]=Math.random()-1/2.0;
                    //+world4[(int)(r/2)][world4[(int)(r/2)].length*x/ring.length];
            world8[(int)(r)]=ring;
        }
        for(double r=0;r<8;r+=1/2.0){
            ring = new double[Math.max(1,(int)(2*Math.ceil(4*Math.PI*Math.sinh(r/2.0)*Math.sinh(r/2.0)-4*Math.PI*Math.sinh((r-1)/2.0)*Math.sinh((r-1)/2.0))))];
            for(int x=0;x<ring.length;x++)ring[x]=Math.random()/2-1/4.0;
                    //+world8[(int)(r)][world8[(int)(r)].length*x/ring.length];
            world16[(int)(r*2)]=ring;
        }
        for(double r=0;r<8;r+=1/4.0){
            ring = new double[Math.max(1,(int)(4*Math.ceil(4*Math.PI*Math.sinh(r/2.0)*Math.sinh(r/2.0)-4*Math.PI*Math.sinh((r-1)/2.0)*Math.sinh((r-1)/2.0))))];
            for(int x=0;x<ring.length;x++)ring[x]=Math.random()/4-1/8.0;
                    //+world16[(int)(r*2)][world16[(int)(r*2)].length*x/ring.length];
            world32[(int)(r*4)]=ring;
        }
        for(double r=0;r<8;r+=1/8.0){
            ring = new double[Math.max(1,(int)(8*Math.ceil(4*Math.PI*Math.sinh(r/2.0)*Math.sinh(r/2.0)-4*Math.PI*Math.sinh((r-1)/2.0)*Math.sinh((r-1)/2.0))))];
            for(int x=0;x<ring.length;x++)ring[x]=Math.random()/8-1/16.0;
                    //+world32[(int)(r*4)][world32[(int)(r*4)].length*x/ring.length];
            world64[(int)(r*8)]=ring;
        }
        for(double r=0;r<8;r+=1/16.0){
            ring = new double[Math.max(1,(int)(16*Math.ceil(4*Math.PI*Math.sinh(r/2.0)*Math.sinh(r/2.0)-4*Math.PI*Math.sinh((r-1)/2.0)*Math.sinh((r-1)/2.0))))];
            for(int x=0;x<ring.length;x++)ring[x]=Math.random()/16-1/32.0;
                    //+world64[(int)(r*8)][(int)(world64[(int)(r*8)].length*(long)x/ring.length)];
            world128[(int)(r*16)]=ring;
        }
        for(double r=0;r<8;r+=1.0/32){
            ring = new double[Math.max(1,(int)(32*Math.ceil(4*Math.PI*Math.sinh(r/2.0)*Math.sinh(r/2.0)-4*Math.PI*Math.sinh((r-1)/2.0)*Math.sinh((r-1)/2.0))))];
            for(int x=0;x<ring.length;x++)ring[x]=Math.random()/32-1/64.0;
                    //+world128[(int)(r*16)][(int)(world128[(int)(r*16)].length*(long)x/ring.length)];
            world[(int)(r*32)]=ring;
        }
        
        
        double r,theta; double alt,LL,LH,HH,HL,H,L; int landpix=0,seapix=0;
        for(int x=0; x<10001; x++){for(int y=0; y<10001; y++){
            if(Math.sqrt((x-5000)*(x-5000)+(y-5000)*(y-5000))<5000)
                r=poinf(Math.sqrt((x-5000)*(x-5000)+(y-5000)*(y-5000))/5000);else
                r=49308698;
            theta=(Math.atan2((y-5000), (x-5000))+2*Math.PI)%(2*Math.PI);
            if(r<8){
                LL=world[(int)(r*32)][(int)(theta*world[(int)(r*32)].length/(2*Math.PI))];
                LH=world[(int)(r*32)][(int)(theta*world[(int)(r*32)].length/(2*Math.PI)+1)%world[(int)(r*32)].length];
                HL=world[Math.min((int)(r*32)+1,255)][(int)(theta*world[Math.min((int)(r*32)+1,255)].length/(2*Math.PI))];
                HH=world[Math.min((int)(r*32)+1,255)][(int)(theta*world[Math.min((int)(r*32)+1,255)].length/(2*Math.PI)+1)%world[Math.min((int)(r*32)+1,255)].length];
                L=LH*(theta*world[(int)(r*32)].length%(2*Math.PI))/(2*Math.PI)+
                        LL*(1-(theta*world[(int)(r*32)].length%(2*Math.PI))/(2*Math.PI));
                H=HH*(theta*world[Math.min((int)(r*32)+1,255)].length%(2*Math.PI))/(2*Math.PI)+
                        HL*(1-(theta*world[Math.min((int)(r*32)+1,255)].length%(2*Math.PI))/(2*Math.PI));
                alt=H*((r*32)%1)+L*(1-(r*32)%1);
                LL=world128[(int)(r*16)][(int)(theta*world128[(int)(r*16)].length/(2*Math.PI))];
                LH=world128[(int)(r*16)][(int)(theta*world128[(int)(r*16)].length/(2*Math.PI)+1)%world128[(int)(r*16)].length];
                HL=world128[Math.min((int)(r*16)+1,127)][(int)(theta*world128[Math.min((int)(r*16)+1,127)].length/(2*Math.PI))];
                HH=world128[Math.min((int)(r*16)+1,127)][(int)(theta*world128[Math.min((int)(r*16)+1,127)].length/(2*Math.PI)+1)%world128[Math.min((int)(r*16)+1,127)].length];
                L=LH*(theta*world128[(int)(r*16)].length%(2*Math.PI))/(2*Math.PI)+
                        LL*(1-(theta*world128[(int)(r*16)].length%(2*Math.PI))/(2*Math.PI));
                H=HH*(theta*world128[Math.min((int)(r*16)+1,127)].length%(2*Math.PI))/(2*Math.PI)+
                        HL*(1-(theta*world128[Math.min((int)(r*16)+1,127)].length%(2*Math.PI))/(2*Math.PI));
                alt+=H*((r*16)%1)+L*(1-(r*16)%1);
                LL=world64[(int)(r*8)][(int)(theta*world64[(int)(r*8)].length/(2*Math.PI))];
                LH=world64[(int)(r*8)][(int)(theta*world64[(int)(r*8)].length/(2*Math.PI)+1)%world64[(int)(r*8)].length];
                HL=world64[Math.min((int)(r*8)+1,63)][(int)(theta*world64[Math.min((int)(r*8)+1,63)].length/(2*Math.PI))];
                HH=world64[Math.min((int)(r*8)+1,63)][(int)(theta*world64[Math.min((int)(r*8)+1,63)].length/(2*Math.PI)+1)%world64[Math.min((int)(r*8)+1,63)].length];
                L=LH*(theta*world64[(int)(r*8)].length%(2*Math.PI))/(2*Math.PI)+
                        LL*(1-(theta*world64[(int)(r*8)].length%(2*Math.PI))/(2*Math.PI));
                H=HH*(theta*world64[Math.min((int)(r*8)+1,63)].length%(2*Math.PI))/(2*Math.PI)+
                        HL*(1-(theta*world64[Math.min((int)(r*8)+1,63)].length%(2*Math.PI))/(2*Math.PI));
                alt+=H*((r*8)%1)+L*(1-(r*8)%1);
                LL=world32[(int)(r*4)][(int)(theta*world32[(int)(r*4)].length/(2*Math.PI))];
                LH=world32[(int)(r*4)][(int)(theta*world32[(int)(r*4)].length/(2*Math.PI)+1)%world32[(int)(r*4)].length];
                HL=world32[Math.min((int)(r*4)+1,31)][(int)(theta*world32[Math.min((int)(r*4)+1,31)].length/(2*Math.PI))];
                HH=world32[Math.min((int)(r*4)+1,31)][(int)(theta*world32[Math.min((int)(r*4)+1,31)].length/(2*Math.PI)+1)%world32[Math.min((int)(r*4)+1,31)].length];
                L=LH*(theta*world32[(int)(r*4)].length%(2*Math.PI))/(2*Math.PI)+
                        LL*(1-(theta*world32[(int)(r*4)].length%(2*Math.PI))/(2*Math.PI));
                H=HH*(theta*world32[Math.min((int)(r*4)+1,31)].length%(2*Math.PI))/(2*Math.PI)+
                        HL*(1-(theta*world32[Math.min((int)(r*4)+1,31)].length%(2*Math.PI))/(2*Math.PI));
                alt+=H*((r*4)%1)+L*(1-(r*4)%1);
                LL=world16[(int)(r*2)][(int)(theta*world16[(int)(r*2)].length/(2*Math.PI))];
                LH=world16[(int)(r*2)][(int)(theta*world16[(int)(r*2)].length/(2*Math.PI)+1)%world16[(int)(r*2)].length];
                HL=world16[Math.min((int)(r*2)+1,15)][(int)(theta*world16[Math.min((int)(r*2)+1,15)].length/(2*Math.PI))];
                HH=world16[Math.min((int)(r*2)+1,15)][(int)(theta*world16[Math.min((int)(r*2)+1,15)].length/(2*Math.PI)+1)%world16[Math.min((int)(r*2)+1,15)].length];
                L=LH*(theta*world16[(int)(r*2)].length%(2*Math.PI))/(2*Math.PI)+
                        LL*(1-(theta*world16[(int)(r*2)].length%(2*Math.PI))/(2*Math.PI));
                H=HH*(theta*world16[Math.min((int)(r*2)+1,15)].length%(2*Math.PI))/(2*Math.PI)+
                        HL*(1-(theta*world16[Math.min((int)(r*2)+1,15)].length%(2*Math.PI))/(2*Math.PI));
                alt+=H*((r*2)%1)+L*(1-(r*2)%1);
                LL=world8[(int)(r)][(int)(theta*world8[(int)(r)].length/(2*Math.PI))];
                LH=world8[(int)(r)][(int)(theta*world8[(int)(r)].length/(2*Math.PI)+1)%world8[(int)(r)].length];
                HL=world8[Math.min((int)(r)+1,7)][(int)(theta*world8[Math.min((int)(r)+1,7)].length/(2*Math.PI))];
                HH=world8[Math.min((int)(r)+1,7)][(int)(theta*world8[Math.min((int)(r)+1,7)].length/(2*Math.PI)+1)%world8[Math.min((int)(r)+1,7)].length];
                L=LH*(theta*world8[(int)(r)].length%(2*Math.PI))/(2*Math.PI)+
                        LL*(1-(theta*world8[(int)(r)].length%(2*Math.PI))/(2*Math.PI));
                H=HH*(theta*world8[Math.min((int)(r)+1,7)].length%(2*Math.PI))/(2*Math.PI)+
                        HL*(1-(theta*world8[Math.min((int)(r)+1,7)].length%(2*Math.PI))/(2*Math.PI));
                alt+=H*((r)%1)+L*(1-(r)%1);
                LL=world4[(int)(0.5*r)][(int)(theta*world4[(int)(0.5*r)].length/(2*Math.PI))];
                LH=world4[(int)(0.5*r)][(int)(theta*world4[(int)(0.5*r)].length/(2*Math.PI)+1)%world4[(int)(0.5*r)].length];
                HL=world4[Math.min((int)(0.5*r)+1,3)][(int)(theta*world4[Math.min((int)(0.5*r)+1,3)].length/(2*Math.PI))];
                HH=world4[Math.min((int)(0.5*r)+1,3)][(int)(theta*world4[Math.min((int)(0.5*r)+1,3)].length/(2*Math.PI)+1)%world4[Math.min((int)(0.5*r)+1,3)].length];
                L=LH*(theta*world4[(int)(0.5*r)].length%(2*Math.PI))/(2*Math.PI)+
                        LL*(1-(theta*world4[(int)(0.5*r)].length%(2*Math.PI))/(2*Math.PI));
                H=HH*(theta*world4[Math.min((int)(0.5*r)+1,3)].length%(2*Math.PI))/(2*Math.PI)+
                        HL*(1-(theta*world4[Math.min((int)(0.5*r)+1,3)].length%(2*Math.PI))/(2*Math.PI));
                alt+=H*((0.5*r)%1)+L*(1-(0.5*r)%1);
                if(alt>=0.222){
                    output.setRGB(x,y,(new Color(0,(int)(127.5*alt),0)).getRGB());
                    landpix++;
                }else {
                    output.setRGB(x,y,(new Color(0,0,(int)(200+100*alt))).getRGB());
                    seapix++;
                }
                if(r%1<0.05)output.setRGB(x,y,(new Color(255,0,0)).getRGB());
            }else{
            }
        }
        if(x%200==0)System.out.println(x);
        }
        try{
            ImageIO.write(output, "png", new File("C:\\Users\\admin\\Pictures\\High-Resolution Hyperbolic Perlin Maps\\"+(landpix/(double)(landpix+seapix))+".png"));
        }catch(IOException e){
            
        }
    }
    
    public static double poinf(double y){
        return 2*Math.log((y+1)/Math.sqrt(1-y));
    }
}
