import React from "react";
import { Popover, Badge } from "antd";
import { BellOutlined } from "@ant-design/icons";
import { useChatSocket } from "../../../api-services/SocketService";

// Added custom animation definitions
const animationStyles = `
@keyframes bell-wiggle {
  0%,100% { transform: rotate(0deg); }
  10% { transform: rotate(-18deg); }
  20% { transform: rotate(14deg); }
  30% { transform: rotate(-12deg); }
  40% { transform: rotate(9deg); }
  50% { transform: rotate(-6deg); }
  60% { transform: rotate(4deg); }
  70% { transform: rotate(-2deg); }
  80% { transform: rotate(1deg); }
  90% { transform: rotate(0deg); }
}
@keyframes ringGlow {
  0% { box-shadow: 0 0 0 0 rgba(34,197,94,.55); }
  70% { box-shadow: 0 0 0 12px rgba(34,197,94,0); }
  100% { box-shadow: 0 0 0 0 rgba(34,197,94,0); }
}
@keyframes pingSmall {
  0% { transform: scale(.55); opacity:.9; }
  80% { transform: scale(2); opacity:0; }
  100% { opacity:0; }
}
@keyframes slideInFade {
  0% { transform: translateY(8px); opacity:0; }
  100% { transform: translateY(0); opacity:1; }
}
.bell-has-notifications { animation: bell-wiggle 2.4s ease-in-out infinite .25s; transform-origin: 50% 0; }
.bell-dot { animation: ringGlow 2.2s ease-out infinite; }
.bell-dot::after {
  content:"";
  position:absolute;
  inset:0;
  border-radius:inherit;
  animation: pingSmall 2.2s cubic-bezier(0,0,.2,1) infinite;
  background:rgba(34,197,94,0.45);
}
.notification-item { animation: slideInFade .45s cubic-bezier(.4,.1,.2,1) forwards; opacity:0; }
`;

const NotificationBell = () => {
  const { notifications, clearNotifications } = useChatSocket();
  const hasNotifications = notifications.length > 0;

  const content = (
    <div className="max-h-80 w-80 overflow-y-auto px-1 py-1">
      {notifications.length === 0 && (
        <p className="text-sm text-gray-500 m-0 py-2 text-center">
          Không có thông báo
        </p>
      )}
      {notifications.map((n, i) => (
        <div
          key={i}
          className="notification-item p-2 mb-2 rounded border !border-gray-100 !bg-gradient-to-br from-white to-gray-50 shadow-sm hover:shadow transition-shadow last:mb-0 group"
          style={{ animationDelay: `${i * 70}ms` }}
        >
          <p className="m-0 text-sm text-gray-800 leading-snug group-hover:text-gray-900">
            {n.message}
          </p>
          {n.url && n.url.trim() !== "" && (
            <a
              href={n.url}
              className="inline-block mt-1 text-xs font-medium text-blue-600 hover:text-blue-700 hover:underline"
            >
              Chi tiết &rarr;
            </a>
          )}
        </div>
      ))}
      {hasNotifications && (
        <div className="pt-1 text-right">
          <button
            onClick={clearNotifications}
            className="text-xs text-red-500 hover:text-red-600 hover:underline focus:outline-none focus:ring focus:ring-red-300/40 rounded px-1"
          >
            Xóa tất cả
          </button>
        </div>
      )}
    </div>
  );

  return (
    <>
      {/* Inject animations once */}
      <style>{animationStyles}</style>
      <Popover
        placement="bottomRight"
        trigger={["hover", "click"]}
        content={content}
        overlayClassName="notification-popover"
      >
        <button
          type="button"
          aria-label="Thông báo"
          aria-haspopup="true"
          className={
            "mx-2 relative flex items-center justify-center !text-white transition-colors rounded-md p-1" +
            (hasNotifications
              ? " bell-has-notifications"
              : " hover:text-blue-200")
          }
        >
          <BellOutlined style={{ fontSize: 32 }} />
          {hasNotifications && (
            <span className="bell-dot absolute top-0 right-0 inline-flex h-2.5 w-2.5 rounded-full bg-green-400 ring-2 ring-white" />
          )}
        </button>
      </Popover>
    </>
  );
};

export default NotificationBell;
