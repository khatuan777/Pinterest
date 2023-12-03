import classNames from 'classnames/bind';
import images from '../../assets/images';
import styles from './Account.module.scss';

const cx = classNames.bind(styles);

function Wrapper({ children }) {
    return (
        <div className={cx('wrapper')}>
            <img className={cx('img')} src={images.login} alt="" />
            <div className={cx('container-text')}>
                <h2 className={cx('content')}>Join the creative world</h2>
            </div>
            {children}
        </div>
    );
}

export default Wrapper;
