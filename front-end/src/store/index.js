import { createStore } from 'vuex'

const store = createStore({
    state: {
        hasLogin: false,
        userInfo: {
            id: 0,
            username: '',
            nickname: '',
            password: '',
            level: 0,
            isTeacher: 0,
        },
        currentCourse: {
            id: 0,
            courseName: '',
        },
        currentTags: [],
    },
    getters: {
        hasLogin: state => {
            let hasLogin = state.hasLogin
            if (!hasLogin) {
                hasLogin = JSON.parse(window.localStorage.getItem('hasLogin'))
                state.hasLogin = hasLogin
            }
            return hasLogin;
        },
        userInfo: state => {
            let userInfo = state.userInfo;
            if (!userInfo || userInfo.id === 0) {
                userInfo = JSON.parse(window.localStorage.getItem('userInfo'))
                state.userInfo = userInfo
            }
            return userInfo;
        },
        currentCourse: state => {
            let currentCourse = state.currentCourse
            if (!currentCourse || currentCourse.id === 0) {
                currentCourse = JSON.parse(sessionStorage.getItem('currentCourse'))
                state.currentCourse = currentCourse
            }
            return currentCourse;
        },
        currentTags: state => {
            let currentTags = state.currentTags
            if (!currentTags || currentTags.length === 0) {
                currentTags = JSON.parse(sessionStorage.getItem('currentTags'))
                state.currentTags = currentTags
            }
            return currentTags;
        },
    },
    mutations: {
        login(state, userInfo) {
            state.hasLogin = true;
            state.userInfo = JSON.parse(JSON.stringify(userInfo));
            window.localStorage.setItem('hasLogin', true);
            window.localStorage.setItem('userInfo', JSON.stringify(userInfo));
        },
        logout(state) {
            state.hasLogin = false;
            state.userInfo = {
                id: null,
                username: '',
                nickname: '',
                password: '',
                level: 0,
                isTeacher: 0,
            };
            window.localStorage.setItem('hasLogin', false);
            window.localStorage.setItem('userInfo', JSON.stringify(state.userInfo));
        },
        setCurrentCourse(state, course) {
            state.currentCourse = JSON.parse(JSON.stringify(course));
            // window.localStorage.setItem('currentCourse', JSON.stringify(course));
        },
        setCurrentTags(state, tags) {
            state.currentTags = JSON.parse(JSON.stringify(tags));
            // window.localStorage.setItem('currentTags', JSON.stringify(tags));
        },
    },
    actions: {
        Login({commit}, provider) {
            commit("login", provider);
        },
        Logout({commit}) {
            commit("logout");
        },
        SetCurrentCourse({commit}, course) {
            commit("setCurrentCourse", course);
        },
        SetCurrentTags({commit}, tags) {
            commit("setCurrentTags", tags);
        },
    },
    modules: {

    }
})

export default store
