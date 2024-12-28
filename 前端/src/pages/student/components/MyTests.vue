<template>
    <div class="my-tests">
        <h3>我的考试</h3>

        <!-- 考试表格 -->
        <table class="table">
            <thead>
                <tr>
                    <th>考试名称</th>
                    <th>开始时间</th>
                    <th>截止时间</th>
                    <th>提交时间</th>
                    <th>成绩</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <!-- 待完成考试 -->
                <tr v-for="exam in pendingExams" :key="exam.id" class="pending-exam">
                    <td class="pending-text">{{ exam.name }}</td>
                    <td class="pending-text">{{ exam.startTime }}</td>
                    <td class="pending-text">{{ exam.endTime }}</td>
                    <td class="pending-text">-</td>
                    <td class="pending-text">-</td>
                    <td>
                        <button @click="startExam(exam)" class="btn start-exam-btn">开始考试</button>
                    </td>
                </tr>
                <!-- 已完成考试 -->
                <tr v-for="exam in completedExams" :key="exam.id" class="completed-exam">
                    <td class="completed-text">{{ exam.name }}</td>
                    <td class="completed-text">{{ exam.startTime }}</td>
                    <td class="completed-text">{{ exam.endTime }}</td>
                    <td class="completed-text">{{ exam.submitTime }}</td>
                    <td class="completed-text">{{ exam.score }}</td>
                    <td>
                        <button @click="viewExam(exam)" class="btn view-exam-btn">查看试卷</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    data() {
        return {
            // 模拟待完成和已完成的考试数据
            pendingExams: [
                { id: 1, name: "Java期末测试", startTime: "2024-12-26 09:00", endTime: "2024-12-26 12:00" },
                { id: 2, name: "生活中的心理学期末测试", startTime: "2024-12-27 10:00", endTime: "2024-12-27 13:00" },
            ],
            completedExams: [
                { id: 3, name: "软件工程期末测试", startTime: "2024-12-20 14:30", endTime: "2024-12-20 16:00", submitTime: "2024-12-20 15:30", score: 95 },
                { id: 4, name: "云计算基础及应用期末测试", startTime: "2024-12-21 16:00", endTime: "2024-12-21 18:00", submitTime: "2024-12-21 17:45", score: 88 },
            ],
        };
    },
    methods: {
        startExam(exam) {
            // 这里可以放置开始考试的逻辑
            alert(`开始考试：${exam.name}`);
        },
        viewExam(exam) {
            // 查看试卷的逻辑
            alert(`查看试卷：${exam.name}`);
        },
    },
};
</script>

<style scoped>
h3 {
    font-size: 1.8rem;
    color: #333;
    margin-bottom: 20px;
}

.my-tests {
    padding: 20px;
}

.table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
}

.table th,
.table td {
    padding: 12px;
    text-align: left;
    border: 1px solid #ddd;
}

.table th {
    background-color: #f2f2f2;
    color: #333;
}

.table td {
    background-color: #fff;
}

.pending-exam .pending-text {
    color: #ff6f61;
    /* 橘红色文字 */
}

.completed-exam .completed-text {
    color: #6c757d;
    /* 灰色文字 */
}

.pending-exam .btn {
    background-color: #cce5ff;
    border: 1px solid #2389f6b9;
    color: #2389f6b9;
}

.completed-exam .btn {
    background-color: #d4f5d4;
    border: 1px solid #8bc34a;
    color: #8bc34a;
}

.btn {
    padding: 0.5rem 1rem;
    font-size: 0.9rem;
    line-height: 1.5;
    border-radius: 0.25rem;
    cursor: pointer;
}</style>
