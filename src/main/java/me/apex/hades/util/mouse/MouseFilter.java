package me.apex.hades.util.mouse;

//a = ?
//b = ?
//c = ?
//p1 = value to smooth?
//p2 = multiplier?
public class MouseFilter {
    private float a, b, c;

    //Smooths mouse input
    public float smooth(float p1, float p2) {
        this.a += p1;
        p1 = (this.a - this.b) * p2;
        this.c += (p1 - this.c) * 0.5F;

        if(p1 > 0.0F && p1 > this.c || p1 < 0.0F && p1 < this.c) {
            p1 = this.c;
        }

        this.b += p1;
        return p1;
    }

    public void reset() {
        a = 0.0F;
        b = 0.0F;
        c = 0.0F;
    }
}
