import EnhancedTable from '../../../components/Table';
import classNames from 'classnames/bind';
import styles from './Post.module.scss';

const cx = classNames.bind(styles);

function Post() {
    const headCells = [
        {
            id: 'id',
            numeric: false,
            disablePadding: true,
            label: 'ID',
        },
        {
            id: 'image_post',
            numeric: true,
            disablePadding: false,
            label: 'Hình ảnh',
        },
        {
            id: 'content_post',
            numeric: true,
            disablePadding: false,
            label: 'Nội dung bài viết',
        },

        {
            id: 'content_report',
            numeric: true,
            disablePadding: false,
            label: 'Nội dung báo cáo',
        },
        {
            id: 'username_approve',
            numeric: true,
            disablePadding: false,
            label: 'Tài khoản phê duyệt',
        },
        {
            id: 'reject',
            numeric: true,
            disablePadding: false,
            label: 'Không duyệt',
        },
        {
            id: 'approve',
            numeric: true,
            disablePadding: false,
            label: 'Duyệt',
        },
    ];
    function createData(
        id,
        // username_reporting,
        // username_reported,
        image_post,
        content_post,
        content_report,
        username_approve,
        reject,
        approve,
    ) {
        return {
            id,
            // username_reporting,
            // username_reported,
            image_post,
            content_post,
            content_report,
            username_approve,
            reject,
            approve,
        };
    }

    const rows = [
        createData(1, 1, 'Cupcake', 67, 4.3, true, false),
        createData(2, 1, 'Donut', 51, 4.9, true, false),
        createData(3, 1, 'Eclair', 24, 6.0, true, false),
        createData(4, 1, 'Frozen yoghurt', 24, 4.0, true, false),
        createData(5, 1, 'Gingerbread', 49, 3.9, true, false),
        createData(6, 1, 'Honeycomb', 87, 6.5, true, false),
        createData(7, 1, 'Ice cream sandwich', 37, 4.3, true, false),
        createData(8, 1, 'Jelly Bean', 94, 0.0, true, false),
        createData(9, 1, 'KitKat', 65, 7.0, true, false),
        createData(10, 1, 'Lollipop', 98, 0.0, true, false),
        createData(11, 1, 'Marshmallow', 81, 2.0, true, false),
        createData(12, 1, 'Nougat', 9, 37.0, true, false),
        createData(13, 1, 'Oreo', 63, 4.0, true, false),
    ];
    return (
        <div className={cx('wrapper')}>
            <EnhancedTable headCells={headCells} rows={rows} noedit={true} title="Quản lý báo cáo bài đăng" />
        </div>
    );
}

export default Post;
