import React from "react";
import {Link} from "react-router-dom";

export default function BookList( props ){

    return (
        <div>
            <button>
                <Link to="/book/Libray" > 챕터3 </Link>
            </button>
            <button>
                <Link to="/book/Clock" > 챕터4 </Link>
            </button>
            <button>
                <Link to="/book/CommentList" > 챕터5 </Link>
            </button>
            <button>
                <Link to="/book/NotificationList" > 챕터6 </Link>
            </button>
            <button>
                <Link to="/book/Accommodate" > 챕터7 </Link>
            </button>
            <button>
                <Link to="/book/ConfirmButton2" > 챕터8 </Link>
            </button>
            <button>
                <Link to="/book/LandingPage" > 챕터9 </Link>
            </button>
            <button>
                <Link to="/chapter10/AttendanceBook" > 챕터10 </Link>
            </button>
            <button>
                <Link to="/chapter11/SignUp" > 챕터11 </Link>
            </button>
            <button>
                <Link to="/chapter12/Calculator" > 챕터12 </Link>
            </button>


        </div>

    );
}
