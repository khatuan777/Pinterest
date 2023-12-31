import classNames from 'classnames/bind';
import { createContext, useContext, useState } from 'react';
import { ConversationContext } from '../../../context/ConversationContext';
import { ThemeContext } from '../../../context/ThemeContext';
import ConversationMenu from './ConversationMenu';
import styles from './ConversationPopper.module.scss';
import MessageBox from './MessageBox';
const cx = classNames.bind(styles);
export const UserIDContext = createContext('');

function ConversationPopper() {
    const { theme } = useContext(ThemeContext);
    const chattingWithList = useContext(ConversationContext);
    const [messageIsShown, setMessageIsShown] = useState(false);
    const [currentInfor, setCurrentInfor] = useState({});
    // useEffect(() => console.log(chattingWithList))
    const changeConversation = (chatWith = '') => {
        if (messageIsShown) {
            setMessageIsShown(false);
            setCurrentInfor({});
        } else {
            chattingWithList.current.forEach((cons) => {
                console.log('cons:' + Object.keys(cons));
                console.log('cons.messages:' + cons.messages);
                if (cons.user.username === chatWith) {
                    setCurrentInfor({
                        conversation_id: cons.conversation.id,
                        name: cons.user.fullname,
                        avatar: cons.user.avatar,
                        messages: cons.messages,
                    });
                }
            });
            setMessageIsShown(true);
        }
    };

    // Set last message again
    const handleGetNewMessage = (conversation_id, messages) => {
        chattingWithList.current.forEach((item) => {
            if (item.conversation.id === conversation_id) {
                item.lastMessage = messages.at(-1).content;
                item.messages = messages;
                // console.log(item);
            }
        });
    };
    return (
        <div className={cx('wrapper-conversation-popper', theme === 'dark' ? 'dark' : '')}>
            {!messageIsShown ? (
                <ConversationMenu handleChange={changeConversation} chattingWithList={chattingWithList.current} />
            ) : (
                <MessageBox
                    handleChange={changeConversation}
                    handleGetNewMessage={handleGetNewMessage}
                    chatWith={currentInfor}
                />
            )}
        </div>
    );
}

export default ConversationPopper;
