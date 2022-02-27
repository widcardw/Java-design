import request from "./request";

export default async function fetchCourses() {
    const res = await request({url: "/course/all", method: "get"});
    if (res.code === "200") {
        return res.data;
    } else {
        return [];
    }
}