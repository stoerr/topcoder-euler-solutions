#include <iostream>
#include <string>
#include <sstream>
#include <iomanip>
//istringstream
//ostringstream

#include <vector>
//bit_vector
#include <list>
#include <queue>
#include <deque>
#include <stack>
#include <set>

#include <map>
//multimap

#include <cmath>
#include <algorithm>


#include <numeric> 
#include <utility>
#include <functional>
#include <iterator>
#include <cctype>
#include <time.h>

using namespace std;

struct BirthdayCake {

inline int numbits(long long val) {
    int ct = 0;
    while(val) {
        ct += val&1;
        val >>= 1;
    }
    return ct;
}

int howManyFriends(vector <string> af, vector <string> fd, int K) {
    map<string,int> m;
    int curr = 0;
    for(int i = 0; i < af.size(); ++i) {
        m[af[i]] = curr++;
    }
    vector<long long> v;
    for(int i =0; i< fd.size(); ++i) {
        long long mask = 0;
        istringstream is(fd[i]);
        string st;
        while(is>>st) {
            if (m.find(st) != m.end()) {
                mask |= 1LL << m[st];
            }
        }
        v.push_back(mask);
    }
    int n = fd.size();
    int res = 0;
    for (int i = 0; i < (1<<n); ++i) {
        long long currmask = 0;
        for (int j = 0; j < n; j++) {
            if(i&(1<<j)) {
                currmask |= v[j];
            }
        }
        if (curr - numbits(currmask) >= K) {
            res >?= numbits(i);
        }
    }
    return res;
}
};
