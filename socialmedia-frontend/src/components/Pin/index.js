import { faChevronDown } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { ClickAwayListener } from '@mui/base/ClickAwayListener';
import Tippy from '@tippyjs/react';
import classNames from 'classnames/bind';
import { useContext, useState } from 'react';
import { NavLink } from 'react-router-dom';
import { io } from 'socket.io-client';
import 'tippy.js/dist/tippy.css';
import { AccountLoginContext } from '../../context/AccountLoginContext';
import * as boardServices from '../../services/boardServices';
import * as pinServices from '../../services/pinServices';
import * as userSavePinServices from '../../services/userSavePinServices';
import * as userServices from '../../services/userServices';
import AccountInfo from '../AccountInfo';
import Button from '../Button';
import DialogConfirmLogin from '../DialogConfirmLogin';
import { AccessIcon, DownloadIcon, EditIcon, ShareIcon } from '../Icons';
import Popper from '../Popper';
import SelectBoardPopper from '../Popper/SelectBoardPopper';
import SharePopper from '../Popper/SharePopper';
import styles from './Pin.module.scss';

const cx = classNames.bind(styles);

const socket = io("http://localhost:3000");
socket.connect((e) => {
    console.log(e);
})

function Pin({ stt, id, image, linkImage, title, userImage, username, pinCreated = false, handleEdit, onSaveResult }) {
    const userLogin = useContext(AccountLoginContext);
    const [activeOptionTop, setActiveOptionTop] = useState(false);
    const [activeOptionBottom, setActiveOptionBottom] = useState(false);
    const [openConfirmLogin, setOpenConfirmLogin] = useState(false);

    const handleOpenShare = () => {
        setActiveOptionBottom(true);
    };

    const handleDisplay = () => {
        setActiveOptionTop(true);
    };
    const handleClickAwaySelectBoard = () => {
        setActiveOptionTop(false);
    };
    const handleClickAwayShare = () => {
        setActiveOptionBottom(false);
    };

    const [data, setData] = useState('');

    const getData = (boardId) => {
        setData(boardId);
    };
    const handleSave = () => {
        const fetchApi = async () => {
            const userId = 1;
            const pinId = id;
            const boardId = data.id;
            console.log(data);
            // Tăng biến đếm để tạo thông báo bài pin liên quan
            localStorage.setItem('pinCount', parseInt(localStorage.getItem('pinCount')) + 1 || 0);
            const user = await userServices.getUserById(userId);
            const pin = await pinServices.getPinById(pinId);
            const board = await boardServices.getBoardById(boardId);

            const userSavePin = { user, pin, board };
            const result = await userSavePinServices.save(userSavePin);
            if (result) {
                onSaveResult(true);
                setData('Chọn bảng');
            }
        };
        if (userLogin !== 0) {
            if (data !== '') {
                fetchApi();
            }
            else {

            }
        } else {
            setOpenConfirmLogin(true);
        }
    };

    const download = (url, title) => {
        const linkSource = `data:image/jpeg;base64,${url}`;
        const downloadLink = document.createElement('a');
        downloadLink.href = linkSource;
        downloadLink.download = `${title}.png`;
        downloadLink.click();
    };

    const openImage = (url) => {
        const link = document.createElement('a');
        link.href = url;
        link.target = '_blank';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    return (
        <>
            <div className={cx('wrapper')}>
                <div className={cx('container-image')}>
                    <NavLink className={(nav) => cx('menu-item')} to={`/pin/${id}`}>
                        <img className={cx('image')} src={image && `data:image/jpeg;base64,${image}`} alt="" />
                    </NavLink>
                    {pinCreated ? null : (
                        <div className={cx('option-top', { active: activeOptionTop })}>
                            <ClickAwayListener onClickAway={handleClickAwaySelectBoard}>
                                <button className={cx('select-board-btn')} onClick={handleDisplay}>
                                    <Popper
                                        idPopper={`selectBoard${stt}`}
                                        contentTitle={data.name || 'Chọn bảng'}
                                        title={<FontAwesomeIcon icon={faChevronDown} />}
                                        className={cx('select-board')}
                                        body={<SelectBoardPopper getData={getData} />}
                                        widthBody="maxContent"
                                    />
                                </button>
                            </ClickAwayListener>

                            <Button className={cx('saveBtn')} red onClick={handleSave}>
                                Lưu
                            </Button>
                        </div>
                    )}
                    <div className={cx('option-bottom', { active: activeOptionBottom })}>
                        {linkImage && (
                            <button onClick={() => openImage(linkImage)} className={cx('btn-text')}>
                                <AccessIcon className={cx('action', 'gUZ', 'R19', 'U9O', 'kVc')} />
                                <span className={cx('link-image')}>{linkImage}</span>
                            </button>
                        )}
                        {pinCreated && (
                            <Tippy delay={[0, 100]} content="Chỉnh sửa" placement="bottom">
                                <button className={cx('btn')} onClick={handleEdit}>
                                    <EditIcon className={cx('action', 'gUZ', 'R19', 'U9O', 'kVc')} />
                                </button>
                            </Tippy>
                        )}

                        <ClickAwayListener onClickAway={handleClickAwayShare}>
                            <button onClick={handleOpenShare} className={cx(pinCreated ? 'btn-end' : 'btn')}>
                                <Popper
                                    idPopper={`share${id}`}
                                    contentTitle={<ShareIcon className={cx('action', 'gUZ', 'R19', 'U9O', 'kVc')} />}
                                    className={cx('share-menu')}
                                    body={<SharePopper />}
                                    widthBody="maxContent"
                                />
                            </button>
                        </ClickAwayListener>

                        {pinCreated ? null : (
                            <Tippy delay={[0, 100]} content="Lưu ảnh" placement="bottom">
                                <button
                                    onClick={() => {
                                        download(image, title);
                                    }}
                                    className={cx('btn-end')}
                                >
                                    <DownloadIcon className={cx('action', 'gUZ', 'R19', 'U9O', 'kVc')} />
                                </button>
                            </Tippy>
                        )}
                    </div>
                </div>
                {pinCreated ? null : (
                    <div className={cx('info-pin')}>
                        {title && <h3>{title}</h3>}
                        <AccountInfo userImage={userImage} username={username} />
                    </div>
                )}
            </div>
            {openConfirmLogin && <DialogConfirmLogin open={openConfirmLogin} setOpen={setOpenConfirmLogin} />}

            {/* <Popover
                id={shareMenuId}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                }}
                transformOrigin={{
                    horizontal: 'center',
                }}
                open={openedShare}
                onClose={handleCloseShare}
                anchorEl={anchorEl}
            >
                <ShareMenu />
            </Popover> */}
        </>
    );
}

export default Pin;
