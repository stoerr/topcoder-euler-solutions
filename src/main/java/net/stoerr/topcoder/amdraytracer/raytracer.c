#include <stdio.h>
#include <math.h>
#define eps 1E-7
#define GRID 1000
double NN;
double PI;
int dir;
double seen;
double hits[GRID][GRID];
typedef struct ellipsoid{
    double cx, cy, cz;
    double a, b, c;
} el;
typedef struct line3{
    double x1, y1, z1;
    double x2, y2, z2;
} l3;
typedef struct point3{
    double x, y, z;
} p3;

int N,M;
el els[100];
double ris[100];

double sqr(double d){ return d * d; }
double rnd(){ return (double)(rand()&0x3fffffff)/0x20000000/2; }

double norm(l3 *l){
    return sqrt(sqr(l->x2-l->x1)+sqr(l->y2-l->y1)+sqr(l->z2-l->z1));
}
double dot(l3 *l1, l3 *l2){
    double x = (l1->x2 - l1->x1) * (l2->x2 - l2->x1);
    double y = (l1->y2 - l1->y1) * (l2->y2 - l2->y1);
    double z = (l1->z2 - l1->z1) * (l2->z2 - l2->z1);
    return x+y+z;
}
double angle(l3 *l1, l3 *l2){
    double d = dot(l1,l2);
    d/=norm(l1);
    d/=norm(l2);
    return acos(d);
}
void printr3(l3 l){
    printf("%lf %lf %lf %lf %lf %lf\n",l.x1,l.y1,l.z1,l.x2,l.y2,l.z2);
}
void rev(l3 *ray){
    ray->x2 = 2*ray->x1-ray->x2;
    ray->y2 = 2*ray->y1-ray->y2;
    ray->z2 = 2*ray->z1-ray->z2;
}

double intersect(el *e, l3 *l){
    double lx1 = l->x1 - e->cx;
    double ly1 = l->y1 - e->cy;
    double lz1 = l->z1 - e->cz;

    double lx2 = l->x2 - e->cx;
    double ly2 = l->y2 - e->cy;
    double lz2 = l->z2 - e->cz;

    lx1 /= e->a;
    ly1 /= e->b;
    lz1 /= e->c;

    lx2 /= e->a;
    ly2 /= e->b;
    lz2 /= e->c;

    double A = sqr(lx2-lx1) + sqr(ly2-ly1) + sqr(lz2 - lz1);
    double B = 2 * (lx1*(lx2-lx1) + ly1*(ly2-ly1) + lz1*(lz2 - lz1));
    double C = sqr(lx1) + sqr(ly1) + sqr(lz1) - 1;

    if(B*B - 4*A*C < 0)return NN;
    double t = (- B - sqrt(B * B - 4 * A * C)) / 2 / A;
    if(t < eps) {
        t = (- B + sqrt(B * B - 4 * A * C)) / 2 / A;
    }
    if( t < eps ) return NN;
    else return t;
}
l3 plane(el *e, p3 *p){
    l3 norm;
    norm.x1 = p->x;
    norm.y1 = p->y;
    norm.z1 = p->z;

    double px = p->x - e->cx;
    double py = p->y - e->cy;
    double pz = p->z - e->cz;

    norm.x2 = px/sqr(e->a) + norm.x1;
    norm.y2 = py/sqr(e->b) + norm.y1;
    norm.z2 = pz/sqr(e->c) + norm.z1;
    
    return norm;
}
l3 reflect(l3 *inc, l3 *normal){//inc.p1 == norm.p1
    double len1 = norm(normal);
    double len2 = norm(inc);
    double d = dot(inc,normal)/len1/len2;
    l3 ret;
    ret.x1 = normal->x1;
    ret.y1 = normal->y1;
    ret.z1 = normal->z1;
    ret.x2 = 2*d*(normal->x2-normal->x1)/len1 - (inc->x2 - inc->x1)/len2 + normal->x1;
    ret.y2 = 2*d*(normal->y2-normal->y1)/len1 - (inc->y2 - inc->y1)/len2 + normal->y1;
    ret.z2 = 2*d*(normal->z2-normal->z1)/len1 - (inc->z2 - inc->z1)/len2 + normal->z1;

    return ret;
}

l3 refract(l3 *inc, l3 *normal, double ratio){
    //inc.p1 == norm.p1
    double len1 = norm(normal);
    double len2 = norm(inc);
    double d = dot(inc,normal)/len1/len2;

    double x = (ratio*d - sqrt(1-sqr(ratio)*(1-sqr(d))));

    l3 ret;
    ret.x1 = normal->x1;
    ret.y1 = normal->y1;
    ret.z1 = normal->z1;
    ret.x2 = x*(normal->x2-normal->x1)/len1 - ratio*(inc->x2 - inc->x1)/len2 + normal->x1;
    ret.y2 = x*(normal->y2-normal->y1)/len1 - ratio*(inc->y2 - inc->y1)/len2 + normal->y1;
    ret.z2 = x*(normal->z2-normal->z1)/len1 - ratio*(inc->z2 - inc->z1)/len2 + normal->z1;

    return ret;
}


void external(l3 l, double intensity, int dep);
int dep = 0;
void internal(l3 l, int idx, double intensity, int dep){
    if(intensity < 1e-3 || dep == 100){
        return;
    }
    double t = intersect(&els[idx],&l);
    p3 p;
    p.x = l.x1 + t * (l.x2-l.x1);
    p.y = l.y1 + t * (l.y2-l.y1);
    p.z = l.z1 + t * (l.z2-l.z1);
    l3 li;
    li.x1 = p.x;
    li.y1 = p.y;
    li.z1 = p.z;
    li.x2 = l.x1;
    li.y2 = l.y1;
    li.z2 = l.z1;
    l3 n = plane(&els[idx],&p);
    rev(&n);

    double pi = angle(&n,&li);
    double pp = sin(pi)*ris[idx];
    double r;

    if(pp > -1 && pp < 1){
        double pt = asin(pp);
        double rs = sqr(sin(pt-pi)/sin(pt+pi));
        double rp = sqr(tan(pt-pi)/tan(pt+pi));
        r = (rs+rp)/2;
        l3 ray = refract(&li,&n,ris[idx]);
        external(ray,intensity*(1-r), dep+1);
    }else{
        r = 1;
    }
    l3 ray = reflect(&li,&n);
    internal(ray,idx,intensity*r,dep+1);
}

void external(l3 l, double intensity, int dep){
    if(intensity < 1e-3 || dep == 100){
        return;
    }
    int i;
    double mt = 1e9;
    int mi = -1;
    for(i = 0; i<N; i++){
        double t = intersect(&els[i],&l);
        if(t < mt){
            mt = t;
            mi = i;
        }
    }
    if(mi != -1){
        p3 p;
        p.x = l.x1 + mt * (l.x2-l.x1);
        p.y = l.y1 + mt * (l.y2-l.y1);
        p.z = l.z1 + mt * (l.z2-l.z1);
        l3 li;
        li.x1 = p.x;
        li.y1 = p.y;
        li.z1 = p.z;
        li.x2 = l.x1;
        li.y2 = l.y1;
        li.z2 = l.z1;
        l3 n = plane(&els[mi],&p);
        double pi = angle(&n,&li);
        double pt = asin(sin(pi)/ris[mi]);
        double rs = sqr(sin(pt-pi)/sin(pt+pi));
        double rp = sqr(tan(pt-pi)/tan(pt+pi));
        double r = (rs+rp)/2;
        l3 ray = refract(&li,&n,1.0/ris[mi]);
        internal(ray,mi, intensity*(1-r), dep+1);
        ray = reflect(&li,&n);
        external(ray,intensity*r,dep+1);
    }else if(l.z1 > l.z2){
        double t = l.z1/(l.z1-l.z2);
        p3 p;
        p.x = l.x1 + (l.x2-l.x1) * t;
        p.y = l.y1 + (l.y2-l.y1) * t;
        p.z = l.z1 + (l.z2-l.z1) * t;
        if(p.x > 0 && p.x < GRID && p.y>0 && p.y < GRID){
            if(dir == 1){
                hits[(int)p.x][(int)p.y]+=intensity;
            }else{
                seen += hits[(int)p.x][(int)p.y]*intensity;
            }
        }
    }
}

int main(){
    PI = acos(0)*2;
    NN = 0.0/0.0;
    scanf("%d%d",&N,&M);
    double lx[10];
    double ly[10];
    double lz[10];
    int i,j,k;
    for(i = 0; i<N; i++){
        scanf("%lf%lf%lf%lf%lf%lf",&els[i].cx,&els[i].cy,&els[i].cz,&els[i].a,&els[i].b,&els[i].c);
        ris[i] = 2;
    }
    for(i = 0; i<M; i++){
        scanf("%lf%lf%lf",&lx[i],&ly[i],&lz[i]);
    }
    l3 l;
    dir = 1;
    for(i = 0;i <GRID; i++){
        for(j = 0;j <GRID; j++){
            hits[i][j] = 0;
        }
    }
    double d1, d2;
    for(j=0;j<1000000;j++){
        if(j % 100000 == 0)
            fprintf(stderr,"%d\n",j);
        double phi = asin(2*rnd()-1);
        double theta = 2*PI*rnd();
            for(i = 0; i<M; i++){
                l.x1 = lx[i];
                l.y1 = ly[i];
                l.z1 = lz[i];
                l.x2 = l.x1+cos(phi)*cos(theta);
                l.y2 = l.y1+cos(phi)*sin(theta);
                l.z2 = l.z1+sin(phi);
                dep = 0;
                external(l,1,0);
            }
    }
    dir = -1;
    for(i = 0;i <GRID; i++){
        fprintf(stderr,"%d\n",i);
        for(j = 0;j <GRID; j++){
            l.x1 = GRID/2;
            l.y1 = GRID/2;
            l.z1 = GRID/2;
            l.x2 = i+0.5;
            l.y2 = j+0.5;
            l.z2 = 0;
            seen = 0;
            external(l,1,0);
            printf("%d %d %lf %lf\n",i,j,hits[i][j],seen);
        }
    }

}

